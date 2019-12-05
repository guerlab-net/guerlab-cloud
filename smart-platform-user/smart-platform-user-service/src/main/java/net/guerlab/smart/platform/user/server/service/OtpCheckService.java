package net.guerlab.smart.platform.user.server.service;

import net.guerlab.smart.platform.user.core.entity.OtpCheckRequest;
import net.guerlab.smart.platform.user.core.entity.OtpCheckResponse;

/**
 * 双因子检查服务
 *
 * @author guer
 */
public interface OtpCheckService {

    /**
     * 双因子认证检查
     *
     * @param request
     *         请求
     * @return 响应
     */
    OtpCheckResponse otpCheck(OtpCheckRequest request);
}
