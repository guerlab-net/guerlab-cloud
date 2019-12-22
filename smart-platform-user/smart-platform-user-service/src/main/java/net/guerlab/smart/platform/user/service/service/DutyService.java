package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.smart.platform.user.service.entity.Duty;

/**
 * 职务服务
 *
 * @author guer
 */
public interface DutyService extends BaseService<Duty, Long> {

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    @Override
    default Class<Duty> getEntityClass() {
        return Duty.class;
    }
}
