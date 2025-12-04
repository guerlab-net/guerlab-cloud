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

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.rsa.RsaKeys;

/**
 * 请求体安全过滤器.
 *
 * @author guer
 */
@Slf4j
public abstract class AbstractBodySecurityFilter implements GatewayFilter, GlobalFilter, Ordered {

	/**
	 * ObjectMapper.
	 */
	protected final ObjectMapper objectMapper;

	/**
	 * 请求体安全配置.
	 */
	protected final BodySecurityProperties properties;

	/**
	 * 创建实例.
	 *
	 * @param objectMapper ObjectMapper
	 * @param properties   请求体安全配置
	 */
	protected AbstractBodySecurityFilter(ObjectMapper objectMapper, BodySecurityProperties properties) {
		this.objectMapper = objectMapper;
		this.properties = properties;
	}

	/**
	 * 获取RSA公/私钥对.
	 *
	 * @param exchange        exchange
	 * @param isRequestFilter 是否是请求过滤器
	 * @return RSA公/私钥对
	 */
	@Nullable
	protected final RsaKeys getRsaKeys(ServerWebExchange exchange, boolean isRequestFilter) {
		String requestMethod = exchange.getRequest().getMethod().name();
		String requestPath = exchange.getRequest().getURI().getPath();

		return properties.getRsaKeys(requestMethod, requestPath, isRequestFilter);
	}
}
