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
package net.guerlab.cloud.auth.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 抽象登录成功信息
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Data
@Schema(name = "AbstractLoginResponse", description = "抽象登录成功信息")
public class AbstractLoginResponse<T> {

    /**
     * accessToken
     */
    @Schema(description = "accessToken")
    protected TokenInfo accessToken;

    /**
     * refreshToken
     */
    @Schema(description = "refreshToken")
    protected TokenInfo refreshToken;

    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    protected T info;
}
