package net.guerlab.cloud.web.core.exception.handler;

/**
 * 全局异常处理日志记录器
 *
 * @author guer
 */
public interface GlobalExceptionLogger {

    /**
     * 记录日志
     *
     * @param e
     *         异常
     * @param requestMethod
     *         请求方法
     * @param requestPath
     *         请求路径
     */
    void debug(Throwable e, String requestMethod, String requestPath);
}
