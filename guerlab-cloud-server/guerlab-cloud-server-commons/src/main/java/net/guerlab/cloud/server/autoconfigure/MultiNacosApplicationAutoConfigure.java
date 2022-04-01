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

package net.guerlab.cloud.server.autoconfigure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.server.properties.NacosServerProperties;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 多实例注册自动配置.
 *
 * @author guer
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(NacosServerProperties.class)
public class MultiNacosApplicationAutoConfigure
		implements ApplicationListener<WebServerInitializedEvent>, DisposableBean {

	/**
	 * 服务注册与发现服务器.
	 */
	private final NamingService namingService;

	/**
	 * 服务发现配置.
	 */
	private final NacosDiscoveryProperties discoveryProperties;

	/**
	 * 服务注册与发现服务器配置.
	 */
	private final NacosServerProperties serverProperties;

	/**
	 * 实例缓存.
	 */
	private final Map<String, Instance> instanceMap = new HashMap<>();

	/**
	 * 项目端口.
	 */
	private final AtomicInteger port = new AtomicInteger(0);

	/**
	 * 通过服务注册与发现服务器配置、服务发现配置初始化、nacos服务管理初始化多实例注册自动配置.
	 *
	 * @param serverProperties    服务注册与发现服务器配置
	 * @param discoveryProperties 服务发现配置
	 * @param nacosServiceManager nacos服务管理
	 */
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	public MultiNacosApplicationAutoConfigure(NacosServerProperties serverProperties,
			NacosDiscoveryProperties discoveryProperties, NacosServiceManager nacosServiceManager) {
		this.serverProperties = serverProperties;
		this.discoveryProperties = discoveryProperties;
		this.namingService = nacosServiceManager.getNamingService(discoveryProperties.getNacosProperties());
	}

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		this.port.compareAndSet(0, event.getWebServer().getPort());

		List<String> appNames = serverProperties.getAppNames();

		if (CollectionUtil.isNotEmpty(appNames)) {
			appNames.forEach(appName -> registerInstance(appName, discoveryProperties));
		}
	}

	/**
	 * 注册实例.
	 *
	 * @param appName             应用名称
	 * @param discoveryProperties 服务发现配置
	 */
	private void registerInstance(String appName, NacosDiscoveryProperties discoveryProperties) {
		String clusterName = StringUtils.trimToNull(appName);
		if (clusterName == null) {
			return;
		}

		Instance instance = new Instance();
		instance.setIp(discoveryProperties.getIp());
		instance.setPort(port.get());
		instance.setClusterName(clusterName);
		instance.setMetadata(discoveryProperties.getMetadata());

		if (instanceMap.putIfAbsent(clusterName, instance) == null) {
			try {
				namingService.registerInstance(clusterName, instance);
			}
			catch (Exception e) {
				throw new ApplicationException(e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void destroy() {
		instanceMap.forEach((clusterName, instance) -> {
			try {
				namingService.deregisterInstance(clusterName, instance);
			}
			catch (Exception e) {
				log.debug(e.getLocalizedMessage(), e);
			}
		});
	}
}
