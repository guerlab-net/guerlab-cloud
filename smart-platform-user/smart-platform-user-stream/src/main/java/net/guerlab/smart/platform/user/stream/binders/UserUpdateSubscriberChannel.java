package net.guerlab.smart.platform.user.stream.binders;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 用户更新消息订阅者
 * <p>
 * bean: net.guerlab.smart.platform.user.core.domain.UserDTO
 *
 * @author guer
 */
public interface UserUpdateSubscriberChannel {

    /**
     * binding名称
     */
    String NAME = "user_update_input";

    /**
     * 构造用户更新消息订阅者
     *
     * @return 用户更新消息订阅者
     */
    @Input(NAME)
    SubscribableChannel input();
}
