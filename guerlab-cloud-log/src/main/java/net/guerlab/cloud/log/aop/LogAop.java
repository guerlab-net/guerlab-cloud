package net.guerlab.cloud.log.aop;

import net.guerlab.cloud.auth.AbstractContextHandler;
import net.guerlab.cloud.log.annotation.Log;
import net.guerlab.cloud.log.handler.LogHandler;
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
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * 保存操作记录切面
 *
 * @author guer
 */
@Aspect
public class LogAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAop.class);

    private final ObjectProvider<List<LogHandler>> logHandlersProvider;

    private final MessageSource messageSource;

    public LogAop(ObjectProvider<List<LogHandler>> logHandlersProvider, MessageSource messageSource) {
        this.logHandlersProvider = logHandlersProvider;
        this.messageSource = messageSource;
    }

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
            Object pointResult = result;
            Throwable throwable = ex;
            logHandlersProvider.ifUnique(handlers -> {
                for (LogHandler handler : handlers) {
                    logHandler(point, log, pointResult, throwable, handler);
                }
            });
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
     * @param handler
     *         日志处理器
     */
    private void logHandler(ProceedingJoinPoint point, Log log, @Nullable Object result, @Nullable Throwable ex,
            LogHandler handler) {
        Signature signature = point.getSignature();
        String method = AbstractContextHandler.getRequestMethod();
        String uri = AbstractContextHandler.getRequestUri();
        String logContent = StringUtils.trimToNull(log.value());
        if (!(signature instanceof MethodSignature) || method == null || uri == null || logContent == null) {
            return;
        }

        MethodSignature methodSignature = (MethodSignature) signature;
        if (!handler.accept(methodSignature, log)) {
            return;
        }

        Object[] args = point.getArgs();
        String[] parameterNames = methodSignature.getParameterNames();

        if (!Objects.equals(args.length, parameterNames.length)) {
            LOGGER.debug("parameter length is {}, but args length is {}", parameterNames.length, args.length);
            return;
        }

        Map<String, Object> params = new HashMap<>(args.length);
        for (int i = 0; i < args.length; i++) {
            params.put(parameterNames[i], args[i]);
        }

        logContent = parseLogContent(logContent, args);

        try {
            handler.handler(logContent, method, uri, params, result, ex);
        } catch (Exception e) {
            LOGGER.debug(e.getLocalizedMessage(), e);
        }
    }

    private String parseLogContent(String logContent, Object[] args) {
        String message = messageSource.getMessage(logContent, args, logContent, Locale.getDefault());
        return message == null ? logContent : message;
    }
}
