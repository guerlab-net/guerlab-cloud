package net.guerlab.smart.platform.basic.auth.properties;

import lombok.Data;

import java.util.Collection;

/**
 * token 工厂配置
 *
 * @author guer
 */
@Data
public class TokenFactoryProperties {

    /**
     * 启用标志
     */
    private boolean enabled = true;

    /**
     * 默认工厂
     */
    private boolean defaultFactory = false;

    /**
     * 允许的IP列表
     */
    private Collection<String> allowIpList;

    /**
     * 拒绝的IP列表
     */
    private Collection<String> denyIpList;

    /**
     * accessToken SigningKey
     */
    private String accessTokenSigningKey = "signingKey";

    /**
     * refreshToken SigningKey
     */
    private String refreshTokenSigningKey = "signingKey";

    /**
     * accessToken 过期时间
     */
    private long accessTokenExpire = 86400000;

    /**
     * refreshToken 过期时间
     */
    private long refreshTokenExpire = 2 * 86400000;
}
