package net.guerlab.smart.platform.user.core.entity;

import lombok.Data;

/**
 * 双因子认证检查响应
 *
 * @author guer
 */
@Data
public class OtpCheckResponse {

    /**
     * 是否通过
     */
    private boolean accept;
}
