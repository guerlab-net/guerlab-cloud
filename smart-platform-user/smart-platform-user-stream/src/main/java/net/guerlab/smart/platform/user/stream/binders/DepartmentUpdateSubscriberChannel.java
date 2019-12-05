package net.guerlab.smart.platform.user.stream.binders;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 部门更新消息订阅者
 * <p>
 * bean: net.guerlab.smart.platform.user.core.domain.DepartmentDTO
 *
 * @author guer
 */
public interface DepartmentUpdateSubscriberChannel {

    /**
     * binding名称
     */
    String NAME = "department_update_input";

    /**
     * 构造部门更新消息订阅者
     *
     * @return 部门更新消息订阅者
     */
    @Input(NAME)
    SubscribableChannel input();
}
