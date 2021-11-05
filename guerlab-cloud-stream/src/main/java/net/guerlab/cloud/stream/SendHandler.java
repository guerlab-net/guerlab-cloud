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

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Objects;

/**
 * 发送处理
 *
 * @param <T>
 *         待发送内容类型
 * @author guer
 */
@SuppressWarnings("unused")
public class SendHandler<T> {

    /**
     * binding名称
     */
    protected final String bindingName;

    /**
     * streamBridge实例
     */
    protected final StreamBridge streamBridge;

    /**
     * 创建发送处理
     *
     * @param bindingName
     *         bindingName
     * @param streamBridge
     *         streamBridge
     */
    public SendHandler(String bindingName, StreamBridge streamBridge) {
        Objects.requireNonNull(bindingName, "bindingName can not be null");
        Objects.requireNonNull(streamBridge, "streamBridge can not be null");
        this.bindingName = formatBindingName(bindingName);
        this.streamBridge = streamBridge;
    }

    /**
     * 格式化bindingName
     *
     * @param bindingName
     *         bindingName
     * @return 格式化后的bindingName
     */
    private static String formatBindingName(String bindingName) {
        if (bindingName.contains(BindingNameConstants.OUT_PARAM_SUFFIX)) {
            return bindingName;
        } else {
            return bindingName + BindingNameConstants.DEFAULT_OUT_PARAM_SUFFIX;
        }
    }

    /**
     * 发送
     *
     * @param payload
     *         待发送内容
     * @return 是否发送成功
     */
    public boolean send(T payload) {
        Objects.requireNonNull(payload, "payload can not be null");

        if (payload instanceof Message) {
            return streamBridge.send(bindingName, payload);
        } else {
            return streamBridge.send(bindingName, buildMessage(payload));
        }
    }

    /**
     * 构造消息体
     *
     * @param payload
     *         消息体内容
     * @return 消息体
     */
    protected Message<T> buildMessage(T payload) {
        MessageBuilder<T> builder = MessageUtils.toBuilder(payload);
        builder.setHeader("spring.cloud.function.definition", MessageUtils.getListenerName(bindingName));
        return builder.build();
    }
}
