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

package net.guerlab.cloud.openapi.webmvc.autoconfigure;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;

/**
 * OpenApi相关路径配置.
 *
 * @author guer
 */
@Slf4j
@Configuration
public class OpenApiWebmvcAutoConfigure {

	/**
	 * 设置http响应数据处理配置参数.
	 *
	 * @param responseAdvisorProperties http响应数据处理配置参数
	 */
	@Autowired(required = false)
	public void responseAdvisorAddExcluded(ResponseAdvisorProperties responseAdvisorProperties) {
		List<String> excluded = List.of(
				"org.springdoc.webmvc.ui.SwaggerWelcomeWebMvc#openapiJson",
				"org.springdoc.webmvc.ui.SwaggerConfigResource#openapiJson",
				"org.springdoc.webmvc.api.OpenApiWebMvcResource#openapiJson",
				"org.springdoc.webmvc.api.OpenApiWebMvcResource#openapiYaml",
				"org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource#openapiJson",
				"org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource#openapiYaml"
		);

		log.debug("add excluded: {}", excluded);

		responseAdvisorProperties.addExcluded(excluded);
	}

}
