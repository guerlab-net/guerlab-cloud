package net.guerlab.cloud.context.core;

import java.util.HashMap;

/**
 * 上下文属性
 *
 * @author guer
 */
public class ContextAttributes extends HashMap<Object, Object> {

    /**
     * 保存KEY
     */
    public static final String KEY = ContextAttributes.class.getName();

    /**
     * 请求保存KEY
     */
    public static final String REQUEST_KEY = KEY + ".request";

    /**
     * 响应保存KEY
     */
    public static final String RESPONSE_KEY = KEY + ".response";

    @Override
    public String toString() {
        return "ContextAttributes: " + super.toString();
    }
}
