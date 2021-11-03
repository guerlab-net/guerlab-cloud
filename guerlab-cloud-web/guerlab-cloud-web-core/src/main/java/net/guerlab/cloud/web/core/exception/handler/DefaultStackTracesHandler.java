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
package net.guerlab.cloud.web.core.exception.handler;

import net.guerlab.cloud.web.core.properties.GlobalExceptionProperties;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import net.guerlab.web.result.ApplicationStackTrace;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.RemoteException;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 默认堆栈处理
 *
 * @author guer
 */
public class DefaultStackTracesHandler implements StackTracesHandler {

    private final GlobalExceptionProperties properties;

    public DefaultStackTracesHandler(GlobalExceptionProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setStackTrace(Fail<?> fail, @Nullable Throwable throwable) {
        if (throwable == null || !properties.isPrintStackTrace()) {
            return;
        }

        fail.setStackTraces(getStackTraces(throwable));
    }

    protected List<ApplicationStackTrace> getStackTraces(@Nullable Throwable throwable) {
        if (throwable == null) {
            return Collections.emptyList();
        }

        List<ApplicationStackTrace> stackTraces = new ArrayList<>();
        setSubStackTrace(stackTraces, throwable);
        return stackTraces;
    }

    protected void setSubStackTrace(List<ApplicationStackTrace> stackTraces, @Nullable Throwable throwable) {
        if (throwable == null) {
            return;
        }
        setSubStackTrace(stackTraces, throwable.getCause());

        if (throwable instanceof RemoteException) {
            stackTraces.add(((RemoteException) throwable).getApplicationStackTrace());
        } else {
            ApplicationStackTrace applicationStackTrace = new ApplicationStackTrace();
            applicationStackTrace.setApplicationName(SpringApplicationContextUtil.getApplicationName());
            applicationStackTrace.setStackTrace(
                    Arrays.stream(throwable.getStackTrace()).map(this::buildStackTraceElementText).filter(Objects::nonNull).collect(Collectors.toList()));

            stackTraces.add(applicationStackTrace);
        }
    }

    @Nullable
    protected String buildStackTraceElementText(StackTraceElement element) {
        String methodKey = element.getClassName() + "." + element.getMethodName();

        if (properties.excludeMatch(methodKey) && !properties.includeMatch(methodKey)) {
            return null;
        }

        return methodKey + ":" + element.getLineNumber();
    }
}