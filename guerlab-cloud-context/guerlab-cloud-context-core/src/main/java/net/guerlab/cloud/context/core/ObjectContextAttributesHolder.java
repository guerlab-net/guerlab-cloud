package net.guerlab.cloud.context.core;

/**
 * 基于对象的上下文属性持有器
 *
 * @author guer
 */
public interface ObjectContextAttributesHolder {

    /**
     * 是否允许处理
     *
     * @param object
     *         对象
     * @return 是否允许处理
     */
    boolean accept(Object object);

    /**
     * 获取上下文属性
     *
     * @param object
     *         对象
     * @return 上下文属性
     */
    ContextAttributes get(Object object);

    /**
     * 设置当前线程的上下文属性
     *
     * @param object
     *         对象
     * @param contextAttributes
     *         上下文属性
     */
    void set(Object object, ContextAttributes contextAttributes);
}
