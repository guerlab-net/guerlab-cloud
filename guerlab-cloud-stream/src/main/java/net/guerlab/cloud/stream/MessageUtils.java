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
package net.guerlab.cloud.stream;

import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Objects;

/**
 * 消息工具类
 *
 * @author guer
 */
@SuppressWarnings("unused")
public class MessageUtils {

    /**
     * 监听器名称前缀
     */
    private static final String LISTENER_NAME_PREFIX = "on";

    /**
     * 来源系统名称
     */
    private static final String HEADER_ORIGIN_APPLICATION_NAME = "spring.cloud.stream.origin.application-name";

    private MessageUtils() {
    }

    /**
     * 判断消息体来源的应用名称是否和期望的应用名称一致
     *
     * @param message
     *         消息体
     * @param appName
     *         应用名称
     * @return 判断
     */
    public static boolean orangeAppNameSame(Message<?> message, String appName) {
        return Objects.equals(toString(message.getHeaders().get(HEADER_ORIGIN_APPLICATION_NAME)), appName);
    }

    /**
     * 判断消息体来源的应用名称是否和当前应用名称一致
     *
     * @param message
     *         消息体
     * @return 判断
     */
    public static boolean orangeAppNameSame(Message<?> message) {
        return orangeAppNameSame(message, getApplicationName());
    }

    /**
     * 获取消息体中的应用名称
     *
     * @param message
     *         消息体
     * @return 源应用名称
     */
    public static String getAppName(Message<?> message) {
        return toString(message.getHeaders().get(HEADER_ORIGIN_APPLICATION_NAME));
    }

    /**
     * 构造消息体构造者
     *
     * @param message
     *         消息
     * @param <T>
     *         消息类型
     * @return 消息体构造者
     */
    public static <T> MessageBuilder<T> toBuilder(T message) {
        MessageBuilder<T> builder = MessageBuilder.withPayload(message);
        builder.setHeader(MessageUtils.HEADER_ORIGIN_APPLICATION_NAME, getApplicationName());
        return builder;
    }

    /**
     * 获取监听器名称
     *
     * @param bindingName
     *         binding名称
     * @return 监听器名称
     */
    public static String getListenerName(String bindingName) {
        if (bindingName.startsWith(LISTENER_NAME_PREFIX)) {
            return bindingName;
        }
        return LISTENER_NAME_PREFIX + bindingName.substring(0, 1).toUpperCase() + bindingName.substring(1);
    }

    /**
     * @param value
     *         获取对象字符串
     * @return 对象字符串
     */
    private static String toString(@Nullable Object value) {
        return value == null ? "" : value.toString();
    }

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    private static String getApplicationName() {
        return SpringApplicationContextUtil.getApplicationName();
    }
}
