package net.guerlab.cloud.stream;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
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

    protected String bindingName;

    protected StreamBridge streamBridge;

    protected MessageChannel messageChannel;

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
     * 创建发送处理
     *
     * @param messageChannel
     *         messageChannel
     */
    public SendHandler(MessageChannel messageChannel) {
        Objects.requireNonNull(messageChannel, "messageChannel can not be null");
        this.messageChannel = messageChannel;
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

        if (messageChannel != null) {
            return messageChannel.send(buildMessage(payload));
        } else {
            return streamBridge.send(bindingName, buildMessage(payload));
        }
    }

    protected Message<?> buildMessage(T payload) {
        if (payload instanceof Message<?>) {
            return (Message<?>) payload;
        }
        MessageBuilder<T> builder = MessageUtils.toBuilder(payload);

        if (bindingName != null) {
            builder.setHeader("spring.cloud.function.definition",
                    "on" + bindingName.substring(0, 1).toUpperCase() + bindingName.substring(1));
        }

        return builder.build();
    }
}
