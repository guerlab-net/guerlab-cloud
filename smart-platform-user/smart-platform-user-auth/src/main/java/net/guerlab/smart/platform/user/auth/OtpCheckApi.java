package net.guerlab.smart.platform.user.auth;

import net.guerlab.smart.platform.user.core.entity.OtpCheckRequest;
import net.guerlab.smart.platform.user.core.entity.OtpCheckResponse;

/**
 * 双因子认证检查处理
 *
 * @author guer
 */
@FunctionalInterface
public interface OtpCheckApi {

    /**
     * 双因子认证检查
     *
     * @param request
     *         检查请求
     * @return 检查响应
     */
    OtpCheckResponse accept(OtpCheckRequest request);
}
