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

package net.guerlab.cloud.gateway.core.bodysecurity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 请求体安全信息.
 *
 * @author guer
 */
@Data
public class BodySecurityInfo {

	/**
	 * 路径安全设置列表.
	 */
	private List<PathSecurityInfo> configs = new ArrayList<>();

	/**
	 * 公钥配置.
	 */
	private String publicKey;
}
