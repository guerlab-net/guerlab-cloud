package net.guerlab.cloud.auth.webflux.test;

import net.guerlab.cloud.log.annotation.Log;
import net.guerlab.cloud.log.handler.LogHandler;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.lang.Nullable;

/**
 * @author guer
 */
public class TestLogHandler implements LogHandler {

    @Override
    public boolean accept(MethodSignature methodSignature, Log log) {
        return true;
    }

    @Override
    public void handler(String logContent, String requestMethod, String requestUri, @Nullable Object operationParam,
            @Nullable Object result, @Nullable Throwable ex) {

    }
}
