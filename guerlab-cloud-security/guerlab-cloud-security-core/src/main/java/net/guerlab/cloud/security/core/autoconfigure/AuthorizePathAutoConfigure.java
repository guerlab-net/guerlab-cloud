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

package net.guerlab.cloud.security.core.autoconfigure;

import java.util.Collections;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import net.guerlab.cloud.security.core.AuthorizePathProvider;
import net.guerlab.cloud.security.core.SimpleAuthorizePathProvider;
import net.guerlab.cloud.security.core.properties.AuthorizePathProperties;
import net.guerlab.cloud.security.core.properties.CorsProperties;

/**
 * 授权路径自动配置.
 *
 * @author guer
 */
@AutoConfiguration
@EnableConfigurationProperties({AuthorizePathProperties.class, CorsProperties.class})
public class AuthorizePathAutoConfigure {

	/**
	 * 根据webEndpointProperties构建授权路径提供者.
	 *
	 * @param webEndpointPropertiesProvider webEndpointProperties提供者
	 * @return 授权路径提供者
	 */
	@Bean
	@ConditionalOnClass(WebEndpointProperties.class)
	@ConditionalOnProperty(prefix = "spring.security.actuator", name = "enabled", havingValue = "true")
	public AuthorizePathProvider webEndpointAuthorizePathProvider(
			ObjectProvider<WebEndpointProperties> webEndpointPropertiesProvider) {
		WebEndpointProperties webEndpointProperties = webEndpointPropertiesProvider.getIfUnique();
		if (webEndpointProperties != null) {
			String path = webEndpointProperties.getBasePath() + "/**";
			return new SimpleAuthorizePathProvider(Collections.singletonList(path));
		}
		else {
			return new SimpleAuthorizePathProvider(Collections.emptyList());
		}
	}

	/**
	 * 全类型请求授权路径提供者.
	 *
	 * @param properties 授权路径配置
	 * @return 授权路径提供者
	 */
	@Bean
	public AuthorizePathProvider allAuthorizePathProvider(AuthorizePathProperties properties) {
		return new SimpleAuthorizePathProvider(properties.getAll());
	}

	/**
	 * GET请求授权路径提供者.
	 *
	 * @param properties 授权路径配置
	 * @return 授权路径提供者
	 */
	@Bean
	public AuthorizePathProvider getAuthorizePathProvider(AuthorizePathProperties properties) {
		return new SimpleAuthorizePathProvider(HttpMethod.GET, properties.getGet());
	}

	/**
	 * HEAD请求授权路径提供者.
	 *
	 * @param properties 授权路径配置
	 * @return 授权路径提供者
	 */
	@Bean
	public AuthorizePathProvider headAuthorizePathProvider(AuthorizePathProperties properties) {
		return new SimpleAuthorizePathProvider(HttpMethod.HEAD, properties.getHead());
	}

	/**
	 * POST请求授权路径提供者.
	 *
	 * @param properties 授权路径配置
	 * @return 授权路径提供者
	 */
	@Bean
	public AuthorizePathProvider postAuthorizePathProvider(AuthorizePathProperties properties) {
		return new SimpleAuthorizePathProvider(HttpMethod.POST, properties.getPost());
	}

	/**
	 * PUT请求授权路径提供者.
	 *
	 * @param properties 授权路径配置
	 * @return 授权路径提供者
	 */
	@Bean
	public AuthorizePathProvider putAuthorizePathProvider(AuthorizePathProperties properties) {
		return new SimpleAuthorizePathProvider(HttpMethod.PUT, properties.getPut());
	}

	/**
	 * PATCH请求授权路径提供者.
	 *
	 * @param properties 授权路径配置
	 * @return 授权路径提供者
	 */
	@Bean
	public AuthorizePathProvider patchAuthorizePathProvider(AuthorizePathProperties properties) {
		return new SimpleAuthorizePathProvider(HttpMethod.PATCH, properties.getPatch());
	}

	/**
	 * DELETE请求授权路径提供者.
	 *
	 * @param properties 授权路径配置
	 * @return 授权路径提供者
	 */
	@Bean
	public AuthorizePathProvider deleteAuthorizePathProvider(AuthorizePathProperties properties) {
		return new SimpleAuthorizePathProvider(HttpMethod.DELETE, properties.getDelete());
	}

	/**
	 * OPTIONS请求授权路径提供者.
	 *
	 * @param properties 授权路径配置
	 * @return 授权路径提供者
	 */
	@Bean
	public AuthorizePathProvider optionsAuthorizePathProvider(AuthorizePathProperties properties) {
		return new SimpleAuthorizePathProvider(HttpMethod.OPTIONS, properties.getOptions());
	}

	/**
	 * TRACE请求授权路径提供者.
	 *
	 * @param properties 授权路径配置
	 * @return 授权路径提供者
	 */
	@Bean
	public AuthorizePathProvider traceAuthorizePathProvider(AuthorizePathProperties properties) {
		return new SimpleAuthorizePathProvider(HttpMethod.TRACE, properties.getTrace());
	}

}
