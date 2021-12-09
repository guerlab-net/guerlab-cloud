/*
 * Copyright 2018-2022 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.guerlab.cloud.idempotent.aspect;

import net.guerlab.cloud.commons.enums.TimeUnitMessageKey;
import net.guerlab.cloud.commons.exception.IdempotentBlockException;
import net.guerlab.cloud.core.util.SpringUtils;
import net.guerlab.cloud.idempotent.annotation.Idempotent;
import net.guerlab.cloud.idempotent.fallback.IdempotentFallbackFactory;
import net.guerlab.cloud.idempotent.fallback.NoopIdempotentFallbackFactory;
import net.guerlab.commons.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Objects;

/**
 * 幂等处理切面
 *
 * @author guer
 */
@Aspect
public class IdempotentAspect {

    private final StringRedisTemplate redisTemplate;

    private final MessageSource messageSource;

    public IdempotentAspect(StringRedisTemplate redisTemplate, MessageSource messageSource) {
        this.redisTemplate = redisTemplate;
        this.messageSource = messageSource;
    }

    /**
     * 日志处理
     *
     * @param point
     *         切入点
     * @param idempotent
     *         幂等注解
     * @return 方法返回信息
     * @throws Throwable
     *         当方法内部抛出异常时候抛出Throwable
     */
    @Around("@annotation(idempotent)")
    public Object handler(ProceedingJoinPoint point, Idempotent idempotent) throws Throwable {
        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) {
            return point.proceed();
        }

        MethodSignature methodSignature = (MethodSignature) signature;
        Object[] args = point.getArgs();

        String lockKey = buildLockKey(methodSignature, args, idempotent);

        boolean lockSuccess = Objects.equals(true, redisTemplate.opsForValue()
                .setIfAbsent(lockKey, "1", idempotent.lockTime(), idempotent.lockTimeUnit()));

        if (lockSuccess) {
            Object result = point.proceed();
            if (idempotent.unlockWhenEndOfOperation()) {
                redisTemplate.delete(lockKey);
            }
            return result;
        }

        Class<? extends IdempotentFallbackFactory<?>> fallbackFactoryClass = idempotent.fallBackFactory();
        if (!NoopIdempotentFallbackFactory.class.equals(fallbackFactoryClass)) {
            return SpringUtils.getBean(fallbackFactoryClass).create(args);
        }

        ApplicationException exception = annotationMessage(idempotent, args);
        if (exception != null) {
            throw exception;
        }

        throw new IdempotentBlockException(idempotent.lockTime(), getTimeUnitName(idempotent));
    }

    /**
     * 构造锁KEY
     *
     * @param methodSignature
     *         方法签名
     * @param args
     *         参数表
     * @param idempotent
     *         幂等注解
     * @return 锁KEY
     */
    private String buildLockKey(MethodSignature methodSignature, Object[] args, Idempotent idempotent) {
        String lockKey;
        String expression = StringUtils.trimToNull(idempotent.lockKey());

        if (expression != null) {
            lockKey = parserExpression(expression, methodSignature.getParameterNames(), args);
            if (lockKey != null) {
                return lockKey;
            }
        }

        return buildLockKeyByMethodSignature(methodSignature);
    }

    /**
     * 解析SpEL表达式
     *
     * @param expression
     *         表达式
     * @param names
     *         参数名列表
     * @param args
     *         参数值列表
     * @return 锁KEY
     */
    @Nullable
    private String parserExpression(String expression, String[] names, Object[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext ctx = new StandardEvaluationContext();

        for (int i = 0; i < names.length; i++) {
            ctx.setVariable(names[i], args[i]);
        }

        return parser.parseExpression(expression).getValue(ctx, String.class);
    }

    /**
     * 构造锁KEY
     *
     * @param methodSignature
     *         方法签名
     * @return 锁KEY
     */
    private String buildLockKeyByMethodSignature(MethodSignature methodSignature) {
        Method method = methodSignature.getMethod();

        Class<?>[] parameterTypes = methodSignature.getParameterTypes();
        String[] parameterNames = methodSignature.getParameterNames();

        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(method.getDeclaringClass().getName());
        keyBuilder.append(".");
        keyBuilder.append(method.getName());
        keyBuilder.append("(");

        if (parameterNames.length > 0) {
            for (int index = 0; index < parameterNames.length; index++) {
                String parameterName = parameterNames[index];
                Class<?> parameterType = parameterTypes[index];

                keyBuilder.append(parameterType.getSimpleName());
                keyBuilder.append(" ");
                keyBuilder.append(parameterName);
                keyBuilder.append(", ");
            }
            keyBuilder.delete(keyBuilder.length() - 2, keyBuilder.length());
        }
        keyBuilder.append(")");

        return keyBuilder.toString();
    }

    /**
     * 根据注解上的消息KEY构造异常
     *
     * @param idempotent
     *         幂等注解
     * @param args
     *         参数列表
     * @return 异常
     */
    @Nullable
    private ApplicationException annotationMessage(Idempotent idempotent, Object[] args) {
        String messageKey = StringUtils.trimToNull(idempotent.messageKey());
        if (messageKey != null) {
            try {
                String message = messageSource.getMessage(messageKey, args, Locale.getDefault());
                return new ApplicationException(message, HttpStatus.TOO_MANY_REQUESTS.value());
            } catch (NoSuchMessageException ignore) {
            }
        }
        return null;
    }

    /**
     * 获取加锁时间单位显示名称
     *
     * @param idempotent
     *         幂等注解
     * @return 加锁时间单位显示名称
     */
    private String getTimeUnitName(Idempotent idempotent) {
        try {
            return messageSource.getMessage(TimeUnitMessageKey.getKey(idempotent.lockTimeUnit()), null,
                    Locale.getDefault());
        } catch (NoSuchMessageException ignore) {
            return "";
        }
    }
}
