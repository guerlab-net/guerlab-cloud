package net.guerlab.cloud.log.aop;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.auth.AbstractContextHandler;
import net.guerlab.cloud.log.handler.LogHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 抽象保存操作记录切面
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class AbstractLogAop {

    /**
     * 日志处理
     *
     * @param point
     *         切入点
     * @param logContent
     *         日志内容
     * @param result
     *         响应数据
     * @param ex
     *         异常信息
     * @param handler
     *         日志处理器
     */
    protected void logHandler(ProceedingJoinPoint point, String logContent, @Nullable Object result,
            @Nullable Throwable ex, LogHandler handler) {
        Signature signature = point.getSignature();
        String method = AbstractContextHandler.getRequestMethod();
        String uri = AbstractContextHandler.getRequestUri();
        if (!(signature instanceof MethodSignature) || method == null || uri == null || handler == null) {
            return;
        }

        MethodSignature methodSignature = (MethodSignature) signature;
        Object[] args = point.getArgs();
        String[] parameterNames = methodSignature.getParameterNames();

        if (!Objects.equals(args.length, parameterNames.length)) {
            log.debug("parameter length is {}, but args length is {}", parameterNames.length, args.length);
            return;
        }

        Map<String, Object> params = new HashMap<>(args.length);
        for (int i = 0; i < args.length; i++) {
            params.put(parameterNames[i], args[i]);
        }

        handler.handler(logContent, method, uri, params, result, ex);
    }
}
