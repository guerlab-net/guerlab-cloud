package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.user.service.entity.UserOauth;
import net.guerlab.smart.platform.user.service.mapper.UserOauthMapper;
import net.guerlab.smart.platform.user.service.service.UserOauthService;
import org.springframework.stereotype.Service;

/**
 * 用户oauth信息服务实现
 *
 * @author guer
 */
@Service
public class UserOauthServiceImpl extends BaseServiceImpl<UserOauth, Long, UserOauthMapper>
        implements UserOauthService {}
