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

package net.guerlab.cloud.gateway.protect;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import net.guerlab.cloud.gateway.UrlDefinition;

/**
 * 接口保护配置.
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = ProtectProperties.PROPERTIES_PREFIX)
public class ProtectProperties {

	/**
	 * 配置前缀.
	 */
	public static final String PROPERTIES_PREFIX = "spring.cloud.gateway.protect";

	/**
	 * 启用标志.
	 */
	private boolean enable;

	/**
	 * 放行请求-请求头名称.
	 */
	private String releaseHeaderName;

	/**
	 * 放行请求-请求头值.
	 */
	private String releaseHeaderValue;

	/**
	 * 待保护路径列表.
	 */
	private List<UrlDefinition> urls = new ArrayList<>();
}
