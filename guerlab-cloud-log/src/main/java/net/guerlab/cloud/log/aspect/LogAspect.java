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
package net.guerlab.cloud.log.aspect;

import net.guerlab.cloud.log.annotation.Log;
import net.guerlab.cloud.log.annotation.LogGroup;
import net.guerlab.cloud.log.handler.LogHandler;
import net.guerlab.cloud.web.core.request.RequestHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * 保存操作记录切面
 *
 * @author guer
 */
@Aspect
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 日志处理对象提供者
     */
    private final ObjectProvider<LogHandler> logHandlersProvider;

    /**
     * messageSource
     */
    private final MessageSource messageSource;

    /**
     * 通过日志处理对象提供者和messageSource初始化日期处理切面
     *
     * @param logHandlersProvider
     *         日志处理对象提供者
     * @param messageSource
     *         messageSource
     */
    public LogAspect(ObjectProvider<LogHandler> logHandlersProvider, MessageSource messageSource) {
        this.logHandlersProvider = logHandlersProvider;
        this.messageSource = messageSource;
    }

    /**
     * 日志处理
     *
     * @param point
     *         切入点
     * @param log
     *         日志注解
     * @return 方法返回信息
     * @throws Throwable
     *         当方法内部抛出异常时候抛出Throwable
     */
    @Around("@annotation(log) && !@annotation(net.guerlab.cloud.auth.annotation.IgnoreLogin)")
    public Object handler(ProceedingJoinPoint point, Log log) throws Throwable {
        Object result = null;
        Throwable ex = null;
        try {
            result = point.proceed();
        } catch (Throwable e) {
            ex = e;
            throw e;
        } finally {
            handler(point, log, result, ex);
        }
        return result;
    }

    /**
     * 日志处理
     *
     * @param point
     *         切入点
     * @param log
     *         日志注解
     * @param result
     *         响应数据
     * @param ex
     *         异常信息
     */
    private void handler(ProceedingJoinPoint point, Log log, @Nullable Object result, @Nullable Throwable ex) {
        Signature signature = point.getSignature();
        String method = RequestHolder.requestMethod();
        String uri = RequestHolder.requestPath();
        String logContent = StringUtils.trimToNull(log.value());
        if (!(signature instanceof MethodSignature methodSignature) || method == null || uri == null
                || logContent == null) {
            return;
        }

        Object[] args = point.getArgs();
        String[] parameterNames = methodSignature.getParameterNames();
        if (!Objects.equals(args.length, parameterNames.length)) {
            LOGGER.debug("parameter length is {}, but args length is {}", parameterNames.length, args.length);
            return;
        }

        LogGroup logGroup = AnnotationUtils.findAnnotation(point.getTarget().getClass(), LogGroup.class);
        String logGroupName = logGroup != null ? StringUtils.trimToNull(logGroup.value()) : null;

        logContent = parseLogContent(logContent, args);
        if (logGroupName != null) {
            logContent = String.format("[%s] %s", logGroupName, logContent);
        }

        String formattedLogContent = logContent;
        Map<String, Object> params = buildParamMap(args, parameterNames);

        logHandlersProvider.stream().filter(handler -> handler.accept(methodSignature, logGroup, log))
                .forEach(handler -> logHandler(formattedLogContent, method, uri, params, result, ex, handler));
    }

    private void logHandler(String logContent, String method, String uri, Map<String, Object> params,
            @Nullable Object result, @Nullable Throwable ex, LogHandler handler) {
        try {
            handler.handler(logContent, method, uri, params, result, ex);
        } catch (Exception e) {
            LOGGER.debug(e.getLocalizedMessage(), e);
        }
    }

    private Map<String, Object> buildParamMap(Object[] args, String[] parameterNames) {
        Map<String, Object> params = new HashMap<>(args.length);
        for (int i = 0; i < args.length; i++) {
            params.put(parameterNames[i], args[i]);
        }
        return params;
    }

    private String parseLogContent(String logContent, Object[] args) {
        String message = messageSource.getMessage(logContent, args, logContent, Locale.getDefault());
        return message == null ? logContent : message;
    }
}
