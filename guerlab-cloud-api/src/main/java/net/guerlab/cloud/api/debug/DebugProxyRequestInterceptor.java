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

package net.guerlab.cloud.api.debug;

import java.util.Objects;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.util.CollectionUtils;

import net.guerlab.cloud.api.properties.DebugProperties;

/**
 * 开发代理请求拦截器.
 *
 * @author guer
 */
@Slf4j
public class DebugProxyRequestInterceptor implements RequestInterceptor {

	/**
	 * http scheme.
	 */
	private static final String SCHEME_HTTP = "http://";

	/**
	 * https scheme.
	 */
	private static final String SCHEME_HTTPS = "https://";

	/**
	 * 路径分隔符.
	 */
	private static final String PATH_SEPARATOR = "/";

	/**
	 * debug配置.
	 */
	private final DebugProperties properties;

	/**
	 * 初始化开发代理请求拦截器.
	 *
	 * @param properties debug配置
	 */
	public DebugProxyRequestInterceptor(DebugProperties properties) {
		this.properties = properties;
	}

	@Override
	public void apply(RequestTemplate requestTemplate) {
		if (!properties.isEnable()) {
			return;
		}

		String serviceName = requestTemplate.feignTarget().name();

		if (serviceName.contains(PATH_SEPARATOR)) {
			serviceName = serviceName.split(PATH_SEPARATOR)[0];
		}
		if (isLocalService(serviceName)) {
			return;
		}

		String targetUrl = requestTemplate.feignTarget().url();
		String url;

		if (targetUrl.startsWith(SCHEME_HTTPS)) {
			url = targetUrl.replace(SCHEME_HTTPS, properties.getGatewayProxyUrl());
		}
		else {
			url = targetUrl.replace(SCHEME_HTTP, properties.getGatewayProxyUrl());
		}

		requestTemplate.target(url);
		requestTemplate.header(properties.getProxyHeaderName(), properties.getProxyHeaderValue());

		log.debug("proxy {} to {}", targetUrl, url);
	}

	/**
	 * 判断是否为本地服务.
	 *
	 * @param serviceName 服务名称
	 * @return 是否为本地服务
	 */
	private boolean isLocalService(String serviceName) {
		if (CollectionUtils.isEmpty(properties.getLocalServices())) {
			return false;
		}

		return properties.getLocalServices().stream().map(StringUtils::trimToNull).filter(Objects::nonNull)
				.anyMatch(service -> service.equalsIgnoreCase(serviceName));
	}
}
