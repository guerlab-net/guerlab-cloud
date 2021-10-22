package net.guerlab.cloud.web.core.exception.handler;

import net.guerlab.web.result.Fail;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;

/**
 * 异常信息构建者
 *
 * @author guer
 */
public interface ResponseBuilder extends Ordered, Comparable<ResponseBuilder> {

    /**
     * 判断是否允许处理
     *
     * @param e
     *         异常
     * @return 是否允许处理
     */
    boolean accept(Throwable e);

    /**
     * 构建异常响应
     *
     * @param e
     *         异常
     * @return 异常响应
     */
    Fail<?> build(Throwable e);

    /**
     * 设置消息源
     *
     * @param messageSource
     *         消息源
     */
    void setMessageSource(MessageSource messageSource);

    /**
     * 设置堆栈处理
     *
     * @param stackTracesHandler
     *         堆栈处理
     */
    void setStackTracesHandler(StackTracesHandler stackTracesHandler);

    /**
     * 排序处理
     *
     * @param o
     *         其他异常信息构建者
     * @return 排序结果
     */
    @Override
    default int compareTo(ResponseBuilder o) {
        return getOrder() - o.getOrder();
    }
}
