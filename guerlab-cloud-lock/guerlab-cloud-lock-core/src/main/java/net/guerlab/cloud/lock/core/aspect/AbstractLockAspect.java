/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.lock.core.aspect;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.beans.BeansException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;

import net.guerlab.cloud.commons.enums.TimeUnitMessageKey;
import net.guerlab.cloud.core.util.SpringUtils;
import net.guerlab.cloud.lock.core.fallback.FallbackFactory;
import net.guerlab.cloud.lock.core.fallback.NoopFallbackFactory;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 抽象锁处理切面.
 *
 * @author guer
 */
@Slf4j
public abstract class AbstractLockAspect {

	/**
	 * messageSource.
	 */
	protected final MessageSource messageSource;

	/**
	 * 创建抽象分布式锁处理切面.
	 *
	 * @param messageSource messageSource
	 */
	protected AbstractLockAspect(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * 构造锁KEY.
	 *
	 * @param methodSignature 方法签名
	 * @param args            参数表
	 * @param lockKey         名称
	 * @return 锁KEY
	 */
	protected String buildLockKey(MethodSignature methodSignature, Object[] args, String lockKey) {
		String expression = StringUtils.trimToNull(lockKey);

		if (expression != null) {
			lockKey = parserExpression(expression, methodSignature.getParameterNames(), args);
			if (lockKey != null) {
				return lockKey;
			}
		}

		return buildLockKeyByMethodSignature(methodSignature);
	}

	/**
	 * 解析SpEL表达式.
	 *
	 * @param expression 表达式
	 * @param names      参数名列表
	 * @param args       参数值列表
	 * @return 锁KEY
	 */
	@Nullable
	protected String parserExpression(String expression, String[] names, Object[] args) {
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext ctx = new StandardEvaluationContext();

		for (int i = 0; i < names.length; i++) {
			ctx.setVariable(names[i], args[i]);
		}

		return parser.parseExpression(expression).getValue(ctx, String.class);
	}

	/**
	 * 根据方法签名构造KEY.
	 *
	 * @param methodSignature 方法签名
	 * @return KEY
	 */
	protected String buildLockKeyByMethodSignature(MethodSignature methodSignature) {
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
	 * 根据消息KEY构造异常.
	 *
	 * @param messageKey 消息Key
	 * @param args       参数列表
	 * @return 异常
	 */
	@Nullable
	protected ApplicationException annotationMessage(String messageKey, Object[] args) {
		messageKey = StringUtils.trimToNull(messageKey);
		if (messageKey != null) {
			try {
				String message = messageSource.getMessage(messageKey, args, Locale.getDefault());
				return new ApplicationException(message, HttpStatus.TOO_MANY_REQUESTS.value());
			}
			catch (NoSuchMessageException ignore) {
				return new ApplicationException(messageKey, HttpStatus.TOO_MANY_REQUESTS.value());
			}
		}
		return null;
	}

	/**
	 * 获取加锁时间单位显示名称.
	 *
	 * @param timeUnit 分布式锁注解
	 * @return 加锁时间单位显示名称
	 */
	protected String getTimeUnitName(TimeUnit timeUnit) {
		try {
			return messageSource.getMessage(TimeUnitMessageKey.getKey(timeUnit), null, Locale.getDefault());
		}
		catch (NoSuchMessageException ignore) {
			return "";
		}
	}

	/**
	 * 获取快速失败结果.
	 *
	 * @param fallbackFactoryClass 快速失败工厂类
	 * @param args                 参数列表
	 * @param <C>                  工厂类型
	 * @return 快速失败结果
	 */
	@Nullable
	protected <C extends FallbackFactory> Object getFallback(Class<C> fallbackFactoryClass, Object[] args) {
		if (NoopFallbackFactory.class.isAssignableFrom(fallbackFactoryClass)) {
			return null;
		}

		FallbackFactory factory = null;
		try {
			factory = SpringUtils.getBean(fallbackFactoryClass);
		}
		catch (BeansException ignore) {
		}

		if (factory == null && !fallbackFactoryClass.isInterface()) {
			try {
				factory = fallbackFactoryClass.getConstructor().newInstance();
			}
			catch (Exception ignored) {
			}
		}

		if (factory == null) {
			return null;
		}
		return factory.create(args);
	}
}
