/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

package net.guerlab.cloud.api.loadbalancer;

import java.util.Map;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.cloud.client.discovery.event.InstancePreRegisteredEvent;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.event.EventListener;

import net.guerlab.cloud.loadbalancer.properties.VersionControlProperties;

/**
 * 负载均衡版本控制请求头注入拦截器.
 *
 * @author guer
 */
@Slf4j
public class LoadBalancerHeaderRequestInterceptor implements RequestInterceptor {

	/**
	 * 版本控制配置.
	 */
	private final VersionControlProperties properties;

	/**
	 * 当前实例.
	 */
	private Registration currentInstance;

	/**
	 * 初始化负载均衡版本控制请求头注入拦截器.
	 *
	 * @param properties 版本控制配置
	 */
	public LoadBalancerHeaderRequestInterceptor(VersionControlProperties properties) {
		this.properties = properties;
	}

	@EventListener
	public void onInstancePreRegisteredEvent(
			InstancePreRegisteredEvent instancePreRegisteredEvent) {
		currentInstance = instancePreRegisteredEvent.getRegistration();
		log.debug("set currentInstance: {}", currentInstance);
	}

	@Override
	public void apply(RequestTemplate template) {
		String requestKey = StringUtils.trimToNull(properties.getRequestKey());
		if (requestKey == null) {
			log.debug("get requestKey fail");
			return;
		}

		if (currentInstance == null) {
			log.debug("current instance is null");
			return;
		}

		Map<String, String> metaData = currentInstance.getMetadata();
		if (metaData == null || metaData.isEmpty()) {
			log.debug("metadata is null or empty");
			return;
		}

		String version = StringUtils.trimToNull(metaData.get(requestKey));
		if (version == null) {
			log.debug("cannot find metadata: {}", requestKey);
			return;
		}

		template.header(requestKey, version);
		log.debug("add header[{}: {}]", requestKey, version);
	}
}
