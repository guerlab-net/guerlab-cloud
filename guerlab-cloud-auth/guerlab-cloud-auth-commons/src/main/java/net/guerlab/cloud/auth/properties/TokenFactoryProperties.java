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

package net.guerlab.cloud.auth.properties;

import java.util.Collection;
import java.util.Collections;

import lombok.Data;

/**
 * token 工厂配置.
 *
 * @author guer
 */
@Data
public class TokenFactoryProperties {

	/**
	 * 启用标志.
	 */
	private boolean enable = false;

	/**
	 * 默认工厂.
	 */
	private boolean defaultFactory = false;

	/**
	 * 排序值.
	 */
	private int order = 0;

	/**
	 * 允许的IP列表.
	 */
	private Collection<String> allowIpList = Collections.emptyList();

	/**
	 * 拒绝的IP列表.
	 */
	private Collection<String> denyIpList = Collections.emptyList();

	/**
	 * accessToken 过期时间.
	 */
	private long accessTokenExpire = 86400000;

	/**
	 * refreshToken 过期时间.
	 */
	private long refreshTokenExpire = 172800000;
}
