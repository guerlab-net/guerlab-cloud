package net.guerlab.cloud.web.core.exception.handler;

import net.guerlab.web.result.Fail;
import org.springframework.lang.Nullable;

/**
 * 堆栈处理
 *
 * @author guer
 */
public interface StackTracesHandler {

    /**
     * 设置堆栈信息
     *
     * @param fail
     *         响应结果
     * @param throwable
     *         异常信息
     */
    void setStackTrace(Fail<?> fail, @Nullable Throwable throwable);
}
