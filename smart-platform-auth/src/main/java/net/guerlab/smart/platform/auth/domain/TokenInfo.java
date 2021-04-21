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
package net.guerlab.smart.platform.auth.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 令牌信息
 *
 * @author guer
 */
@Data
@Schema(name = "TokenInfo", description = "登录成功信息")
public class TokenInfo {

    /**
     * 令牌
     */
    @Schema(description = "令牌")
    private String token;

    /**
     * 过期时间
     */
    @Schema(description = "过期时间")
    private LocalDateTime expireAt;

    /**
     * 过期时长
     */
    @Schema(description = "过期时长")
    private Long expire;
}

