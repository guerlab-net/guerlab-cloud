package net.guerlab.smart.platform.user.service.service;

import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.smart.platform.user.service.entity.UserOauth;

/**
 * 用户oauth信息服务
 *
 * @author guer
 */
public interface UserOauthService extends BaseService<UserOauth, Long> {

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    @Override
    default Class<UserOauth> getEntityClass() {
        return UserOauth.class;
    }
}
