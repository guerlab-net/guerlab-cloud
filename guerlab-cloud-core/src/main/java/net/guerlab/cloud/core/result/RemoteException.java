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

package net.guerlab.cloud.core.result;

import lombok.Getter;
import net.guerlab.commons.collection.CollectionUtil;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 远端异常
 *
 * @author guer
 */
public class RemoteException extends RuntimeException {

    /**
     * 应用堆栈跟踪
     */
    @Getter
    private final ApplicationStackTrace applicationStackTrace;

    private RemoteException(String message, ApplicationStackTrace applicationStackTrace,
            @Nullable RemoteException cause) {
        super(message, cause);
        this.applicationStackTrace = applicationStackTrace;
    }

    /**
     * 构造远端异常
     *
     * @param message
     *         异常信息
     * @param applicationStackTraces
     *         应用堆栈列表
     * @return 远端异常
     */
    @Nullable
    public static RemoteException build(String message, List<ApplicationStackTrace> applicationStackTraces) {
        if (CollectionUtil.isEmpty(applicationStackTraces)) {
            return new RemoteException(message, new ApplicationStackTrace(), null);
        }

        RemoteException exception = null;
        for (int i = applicationStackTraces.size() - 1; i >= 0; i--) {
            exception = new RemoteException(message, applicationStackTraces.get(i), exception);
        }

        return exception;
    }

    private static void fillApplicationStackTrace(List<ApplicationStackTrace> list, Throwable cause) {
        if (!(cause instanceof RemoteException e)) {
            return;
        }

        list.add(e.applicationStackTrace);
        fillApplicationStackTrace(list, e.getCause());
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        if (applicationStackTrace.getStackTrace() == null) {
            return new StackTraceElement[] {};
        }
        return applicationStackTrace.getStackTrace().stream().map(this::buildStackTraceElement).toList()
                .toArray(new StackTraceElement[] {});
    }

    private StackTraceElement buildStackTraceElement(String stackTrace) {
        int lastPointIndex = stackTrace.lastIndexOf(".");
        int colonIndex = stackTrace.lastIndexOf(":");
        String declaringClass = stackTrace.substring(0, lastPointIndex);
        String methodName = stackTrace.substring(lastPointIndex + 1, colonIndex);
        int lineNumber = Integer.parseInt(stackTrace.substring(colonIndex + 1));

        return new StackTraceElement(declaringClass, methodName, "", lineNumber);
    }

    /**
     * 获取应用堆栈跟踪列表
     *
     * @return 应用堆栈跟踪列表
     */
    public List<ApplicationStackTrace> getApplicationStackTraces() {
        List<ApplicationStackTrace> list = new ArrayList<>();
        fillApplicationStackTrace(list, this);
        return list;
    }
}
