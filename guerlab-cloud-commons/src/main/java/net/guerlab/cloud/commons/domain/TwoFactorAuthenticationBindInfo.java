/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

package net.guerlab.cloud.commons.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 双重认证信息绑定资料.
 *
 * @author guer
 */
@Data
@Schema(name = "TwoFactorAuthenticationBindInfo", description = "双重认证信息绑定资料")
public class TwoFactorAuthenticationBindInfo {

	/**
	 * 密钥.
	 */
	@Schema(description = "密钥", requiredMode = Schema.RequiredMode.REQUIRED)
	private String secretKey;

	/**
	 * 二维码内容.
	 */
	@Schema(description = "二维码内容", requiredMode = Schema.RequiredMode.REQUIRED)
	private String qrBarcode;
}
