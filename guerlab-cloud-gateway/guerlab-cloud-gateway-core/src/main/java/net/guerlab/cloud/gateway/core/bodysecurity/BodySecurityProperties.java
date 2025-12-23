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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.lang.Nullable;

import net.guerlab.cloud.rsa.RsaKeys;

/**
 * 请求体安全配置.
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = BodySecurityProperties.PROPERTIES_PREFIX)
public class BodySecurityProperties {

	/**
	 * 配置前缀.
	 */
	public static final String PROPERTIES_PREFIX = "spring.cloud.gateway.body-security";

	/**
	 * 是否启用全局请求加密.
	 */
	private boolean enabledGlobalRequestSecurity;

	/**
	 * 是否启用全局响应加密.
	 */
	private boolean enabledGlobalResponseSecurity;

	/**
	 * RSA公/私钥对配置.
	 */
	private RsaKeys rsaKeys = new RsaKeys();

	/**
	 * 路径安全设置列表.
	 */
	private List<PathSecurityConfig> configs = new ArrayList<>();

	/**
	 * 获取RSA公/私钥对.
	 *
	 * @param requestMethod   请求方法
	 * @param requestPath     请求路径
	 * @param isRequestFilter 是否是请求过滤器
	 * @return RSA公/私钥对
	 */
	@Nullable
	public final RsaKeys getRsaKeys(String requestMethod, String requestPath, boolean isRequestFilter) {
		RsaKeys key = null;
		if (isRequestFilter) {
			if (isEnabledGlobalRequestSecurity()) {
				key = getRsaKeys();
			}
		}
		else {
			if (isEnabledGlobalResponseSecurity()) {
				key = getRsaKeys();
			}
		}

		if (key != null) {
			return key;
		}

		PathSecurityConfig currentConfig = null;

		for (PathSecurityConfig config : getConfigs()) {
			if (acceptOneConfig(requestMethod, requestPath, config, isRequestFilter)) {
				currentConfig = config;
			}
		}
		if (currentConfig == null) {
			return null;
		}

		key = currentConfig.getRsaKeys();
		if (key == null) {
			key = getRsaKeys();
		}
		return key;
	}

	private boolean acceptOneConfig(String requestMethod, String requestPath, PathSecurityConfig config, boolean isRequestFilter) {
		if (isRequestFilter) {
			if (!config.isEnabledRequestSecurity()) {
				return false;
			}
		}
		else {
			if (!config.isEnabledResponseSecurity()) {
				return false;
			}
		}
		return config.getUrls().stream().anyMatch(url -> url.match(requestMethod, requestPath));
	}
}
