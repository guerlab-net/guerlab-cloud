package net.guerlab.smart.platform.user.core.entity;

import lombok.Data;

/**
 * 双因子认证检查请求
 *
 * @author guer
 */
@Data
public class OtpCheckRequest {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 一次性密码
     */
    private String otp;
}
