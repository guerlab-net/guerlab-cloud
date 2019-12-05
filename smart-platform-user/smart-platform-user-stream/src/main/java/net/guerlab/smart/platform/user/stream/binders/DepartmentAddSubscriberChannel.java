package net.guerlab.smart.platform.user.stream.binders;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 部门添加消息订阅者
 * <p>
 * bean: net.guerlab.smart.platform.user.core.domain.DepartmentDTO
 *
 * @author guer
 */
public interface DepartmentAddSubscriberChannel {

    /**
     * binding名称
     */
    @SuppressWarnings("WeakerAccess")
    String NAME = "department_add_input";

    /**
     * 构造部门添加消息订阅者
     *
     * @return 部门添加消息订阅者
     */
    @Input(NAME)
    SubscribableChannel input();
}
