/*
 * Copyright 2018-2024 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.guerlab.cloud.auth.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 令牌信息.
 *
 * @author guer
 */
@Data
@Schema(name = "TokenInfo", description = "登录成功信息")
public class TokenInfo {

	/**
	 * 令牌.
	 */
	@Schema(description = "令牌")
	private String token;

	/**
	 * 过期时间.
	 */
	@Schema(description = "过期时间")
	private LocalDateTime expireAt;

	/**
	 * 过期时长.
	 */
	@Schema(description = "过期时长")
	private Long expire;
}

