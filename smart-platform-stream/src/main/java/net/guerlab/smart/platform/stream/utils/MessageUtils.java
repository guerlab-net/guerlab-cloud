/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.smart.platform.stream.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Objects;

/**
 * 消息工具类
 *
 * @author guer
 */
public class MessageUtils {

    private static final String ORANGE_APP_NAME = "orange-app-name";

    private MessageUtils() {
    }

    /**
     * 读取消息体中的数据
     *
     * @param message
     *         消息体
     * @param clazz
     *         待转换格式
     * @param <T>
     *         目标格式乐新g
     * @return 消息题中的数据
     */
    public static <T> T read(Message<String> message, Class<T> clazz) {
        String payload = message.getPayload();

        if (StringUtils.isBlank(payload)) {
            return null;
        }

        try {
            return getMapper().readValue(payload, clazz);
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 获取ObjectMapper
     *
     * @return ObjectMapper
     */
    private static ObjectMapper getMapper() {
        return SpringApplicationContextUtil.getContext().getBean(ObjectMapper.class);
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
        return Objects.equals(toString(message.getHeaders().get(ORANGE_APP_NAME)), appName);
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
    @SuppressWarnings("unused")
    public static String getAppName(Message<?> message) {
        return toString(message.getHeaders().get(ORANGE_APP_NAME));
    }

    /**
     * @param value
     *         获取对象字符串
     * @return 对象字符串
     */
    private static String toString(Object value) {
        return value == null ? "" : value.toString();
    }

    /**
     * @param channel
     *         消息通道
     * @param builder
     *         消息构造器
     * @return 消息是否已发送
     */
    public static boolean send(MessageChannel channel, MessageBuilder<?> builder) {
        builder.setHeader(MessageUtils.ORANGE_APP_NAME, getApplicationName());

        return channel.send(builder.build());
    }

    /**
     * @param channel
     *         消息通道
     * @param builder
     *         消息构造器
     * @param timeout
     *         超时时间
     * @return 消息是否已发送
     */
    public static boolean send(MessageChannel channel, MessageBuilder<?> builder, long timeout) {
        builder.setHeader(MessageUtils.ORANGE_APP_NAME, getApplicationName());

        return channel.send(builder.build(), timeout);
    }

    /**
     * @param channel
     *         消息通道
     * @param message
     *         消息体
     * @param <T>
     *         消息题类型
     * @return 消息是否已发送
     */
    @SuppressWarnings("UnusedReturnValue")
    public static <T> boolean send(MessageChannel channel, T message) {
        return send(channel, toBuilder(message));
    }

    /**
     * @param channel
     *         消息通道
     * @param message
     *         消息体
     * @param timeout
     *         超时时间
     * @param <T>
     *         消息题类型
     * @return 消息是否已发送
     */
    public static <T> boolean send(MessageChannel channel, T message, long timeout) {
        return send(channel, toBuilder(message), timeout);
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
    private static <T> MessageBuilder<String> toBuilder(T message) {
        try {
            return MessageBuilder.withPayload(getMapper().writeValueAsString(message));
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 获取环境信息
     *
     * @return 环境信息
     */
    private static Environment getEnvironment() {
        return SpringApplicationContextUtil.getContext().getEnvironment();
    }

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    private static String getApplicationName() {
        return getEnvironment().getProperty("spring.application.name");
    }
}
