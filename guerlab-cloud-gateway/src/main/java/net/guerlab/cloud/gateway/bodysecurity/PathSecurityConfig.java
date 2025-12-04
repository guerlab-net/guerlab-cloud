/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

package net.guerlab.cloud.gateway.bodysecurity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import net.guerlab.cloud.gateway.UrlDefinition;
import net.guerlab.cloud.rsa.RsaKeys;

/**
 * 路径安全设置.
 *
 * @author guer
 */
@Data
public class PathSecurityConfig {

	/**
	 * 是否启用请求加密.
	 */
	private boolean enabledRequestSecurity;

	/**
	 * 是否启用响应加密.
	 */
	private boolean enabledResponseSecurity;

	/**
	 * RSA公/私钥对配置.
	 */
	private RsaKeys rsaKeys;

	/**
	 * 处理路径.
	 */
	private List<UrlDefinition> urls = new ArrayList<>();
}
