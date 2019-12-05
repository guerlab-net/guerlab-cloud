package net.guerlab.smart.platform.basic.auth.properties;

import lombok.Data;

/**
 * jwt配置
 *
 * @author guer
 */
@Data
public class JwtProperties {

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
