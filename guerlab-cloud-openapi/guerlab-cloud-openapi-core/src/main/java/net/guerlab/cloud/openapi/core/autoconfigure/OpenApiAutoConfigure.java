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

package net.guerlab.cloud.openapi.core.autoconfigure;

import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.SpringDocAnnotationsUtils;
import org.springdoc.core.SpringDocConfigProperties;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.openapi.core.properties.OpenApiProperties;
import net.guerlab.cloud.security.core.AuthorizePathProvider;
import net.guerlab.cloud.security.core.SimpleAuthorizePathProvider;
import net.guerlab.cloud.security.core.autoconfigure.AuthorizePathAutoConfigure;
import net.guerlab.cloud.web.core.annotation.RequestIp;

import static org.springdoc.core.Constants.SPRINGDOC_ENABLED;

/**
 * OpenApi相关路径配置.
 *
 * @author guer
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OpenApiProperties.class)
@AutoConfigureBefore(AuthorizePathAutoConfigure.class)
public class OpenApiAutoConfigure {

	static {
		SpringDocAnnotationsUtils.addAnnotationsToIgnore(RequestIp.class);
	}

	/**
	 * 根据SpringDocConfigProperties构建授权路径提供者.
	 *
	 * @param properties SpringDocConfigProperties
	 * @return 授权路径提供者
	 */
	@Bean
	@ConditionalOnProperty(name = SPRINGDOC_ENABLED, matchIfMissing = true)
	public AuthorizePathProvider openApiAuthorizePathProvider(SpringDocConfigProperties properties) {
		String path = StringUtils.trimToEmpty(properties.getApiDocs().getPath()) + "/**";
		return new SimpleAuthorizePathProvider(Collections.singletonList(path));
	}
}
