package net.guerlab.smart.platform.user.stream.binders;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 部门更新消息发送者
 * <p>
 * bean: net.guerlab.smart.platform.user.core.domain.DepartmentDTO
 *
 * @author guer
 */
public interface DepartmentUpdateSenderChannel {

    /**
     * binding名称
     */
    @SuppressWarnings("WeakerAccess")
    String NAME = "department_update_output";

    /**
     * 构造部门更新消息发送者
     *
     * @return 部门更新消息发送者
     */
    @Output(NAME)
    MessageChannel output();
}
