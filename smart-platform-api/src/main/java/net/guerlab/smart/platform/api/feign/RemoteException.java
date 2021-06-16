package net.guerlab.smart.platform.api.feign;

import net.guerlab.commons.collection.CollectionUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 远端异常
 *
 * @author guer
 */
public class RemoteException extends RuntimeException {

    private final List<String> stackTraces;

    private RemoteException(String message, List<String> stackTraces, RemoteException cause) {
        super(message, cause);
        this.stackTraces = stackTraces;
    }

    public static RemoteException build(String message, List<List<String>> stackTraces) {
        if (CollectionUtil.isEmpty(stackTraces)) {
            return new RemoteException(message, Collections.emptyList(), null);
        }

        RemoteException exception = null;
        for (int i = stackTraces.size() - 1; i >= 0; i--) {
            exception = new RemoteException(message, stackTraces.get(i), exception);
        }

        return exception;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return stackTraces.stream().map(this::buildStackTraceElement).collect(Collectors.toList())
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
}
