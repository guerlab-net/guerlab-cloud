package net.guerlab.smart.platform.user.stream.binders;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 用户更新消息发送者
 * <p>
 * bean: net.guerlab.smart.platform.user.core.domain.UserDTO
 *
 * @author guer
 */
public interface UserUpdateSenderChannel {

    /**
     * binding名称
     */
    @SuppressWarnings("WeakerAccess")
    String NAME = "user_update_output";

    /**
     * 构造用户更新消息发送者
     *
     * @return 用户更新消息发送者
     */
    @Output(NAME)
    MessageChannel output();
}
