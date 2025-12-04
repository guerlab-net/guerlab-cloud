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

package net.guerlab.cloud.web.core.autoconfigure;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import net.guerlab.cloud.web.core.endpoints.ResponseAdviceEndpoint;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;

/**
 * endpoint's response advisor ignore configure.
 *
 * @author guer
 */
@AutoConfiguration
@EnableConfigurationProperties(ResponseAdvisorProperties.class)
@ConditionalOnClass(name = "org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties")
public class EndpointResponseAdvisorPropertiesAutoConfigure {

	/**
	 * 基础路径设置.
	 *
	 * @param webEndpointPropertiesObjectProvider WebEndpointProperties提供者
	 * @param responseAdvisorProperties           http响应数据处理配置参数
	 */
	@Autowired
	public void basePathHandler(ObjectProvider<WebEndpointProperties> webEndpointPropertiesObjectProvider,
			ResponseAdvisorProperties responseAdvisorProperties) {
		webEndpointPropertiesObjectProvider.ifUnique(webEndpointProperties -> {
			String basePath = StringUtils.trimToNull(webEndpointProperties.getBasePath());
			if (basePath != null) {
				responseAdvisorProperties.addExcluded(basePath + "/**");
			}
		});
	}

	/**
	 * 构造响应环绕监控端点.
	 *
	 * @param responseAdvisorProperties http响应数据处理配置参数
	 * @return 响应环绕监控端点
	 */
	@Bean
	@ConditionalOnMissingBean
	public ResponseAdviceEndpoint responseAdviceEndpoint(ResponseAdvisorProperties responseAdvisorProperties) {
		return new ResponseAdviceEndpoint(responseAdvisorProperties);
	}
}
