package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.smart.platform.user.service.entity.Position;

/**
 * 职位服务
 *
 * @author guer
 */
public interface PositionService extends BaseService<Position, Long> {

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    @Override
    default Class<Position> getEntityClass() {
        return Position.class;
    }
}
