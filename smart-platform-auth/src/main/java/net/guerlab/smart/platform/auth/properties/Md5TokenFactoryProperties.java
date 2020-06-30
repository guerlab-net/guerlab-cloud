package net.guerlab.smart.platform.auth.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * md5 token 工厂配置
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Md5TokenFactoryProperties extends TokenFactoryProperties {

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
