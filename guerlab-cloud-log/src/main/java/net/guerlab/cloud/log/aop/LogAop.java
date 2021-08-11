package net.guerlab.cloud.log.aop;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.log.annotation.Log;
import net.guerlab.cloud.log.handler.LogHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.ObjectProvider;

import java.util.List;

/**
 * 保存操作记录切面
 *
 * @author guer
 */
@Slf4j
@Aspect
public class LogAop extends AbstractLogAop {

    private final ObjectProvider<List<LogHandler>> logHandlersProvider;

    public LogAop(ObjectProvider<List<LogHandler>> logHandlersProvider) {
        this.logHandlersProvider = logHandlersProvider;
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
                    if (handler.accept(log.type())) {
                        logHandler(point, log.value(), pointResult, throwable, handler);
                    }
                }
            });
        }
        return result;
    }
}
