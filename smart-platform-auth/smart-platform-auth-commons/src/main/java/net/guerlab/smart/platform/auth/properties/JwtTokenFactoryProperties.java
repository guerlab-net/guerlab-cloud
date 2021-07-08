/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.smart.platform.auth.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * jwt token 工厂配置
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JwtTokenFactoryProperties extends TokenFactoryProperties {

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
