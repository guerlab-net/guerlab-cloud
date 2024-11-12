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

package net.guerlab.cloud.auth.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * redis token 工厂配置.
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RedisTokenFactoryProperties extends TokenFactoryProperties {

	/**
	 * accessToken key 长度.
	 */
	private int accessTokenKeyLength;

	/**
	 * refreshToken key 长度.
	 */
	private int refreshTokenKeyLength;
}
