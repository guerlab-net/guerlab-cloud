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

package net.guerlab.cloud.security.webflux.autoconfigure;

import java.util.List;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;

import net.guerlab.cloud.security.core.AuthorizePathProvider;
import net.guerlab.cloud.security.core.autoconfigure.AuthorizePathAutoConfigure;
import net.guerlab.cloud.security.core.properties.AuthenticationTypeProperties;
import net.guerlab.cloud.security.core.properties.DefaultCorsConfiguration;

/**
 * WebFlux安全配置.
 *
 * @author guer
 */
@Slf4j
@EnableWebFluxSecurity
@AutoConfiguration(after = AuthorizePathAutoConfigure.class)
public class WebFluxSecurityAutoConfigure {

	private final ObjectProvider<CorsConfiguration> configProvider;

	private final ObjectProvider<AuthorizePathProvider> authorizePathProviders;

	/**
	 * 构造WebFlux安全配置.
	 *
	 * @param configProvider         CorsConfiguration
	 * @param authorizePathProviders 授权路径提供者
	 */
	public WebFluxSecurityAutoConfigure(ObjectProvider<CorsConfiguration> configProvider,
			ObjectProvider<AuthorizePathProvider> authorizePathProviders) {
		this.configProvider = configProvider;
		this.authorizePathProviders = authorizePathProviders;
	}

	/**
	 * 构造SecurityWebFilterChain.
	 *
	 * @param http                           ServerHttpSecurity
	 * @param authenticationTypeProperties   Http安全认证方式配置
	 * @param httpBasicConfigurerCustomizers httpBasic认证方式配置监听器列表
	 * @param formLoginConfigurerCustomizers 登录表单认证方式配置监听器列表
	 * @return SecurityWebFilterChain
	 */
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(
			ServerHttpSecurity http,
			AuthenticationTypeProperties authenticationTypeProperties,
			ObjectProvider<Customizer<ServerHttpSecurity.HttpBasicSpec>> httpBasicConfigurerCustomizers,
			ObjectProvider<Customizer<ServerHttpSecurity.FormLoginSpec>> formLoginConfigurerCustomizers
	) {
		if (authenticationTypeProperties.isEnableHttpBasic()) {
			http.httpBasic(Customizer.withDefaults());
			log.info("enabled httpBasic");
			for (Customizer<ServerHttpSecurity.HttpBasicSpec> customizer : httpBasicConfigurerCustomizers.stream()
					.toList()) {
				log.info("add httpBasic Configurer customizer: {}", customizer);
				http.httpBasic(customizer);
			}
		}
		else {
			http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
			log.info("disabled httpBasic");
		}

		if (authenticationTypeProperties.isEnableFormLogin()) {
			http.formLogin(Customizer.withDefaults());
			log.info("enabled formLogin");
			for (Customizer<ServerHttpSecurity.FormLoginSpec> customizer : formLoginConfigurerCustomizers.stream()
					.toList()) {
				log.info("add formLogin Configurer customizer: {}", customizer);
				http.formLogin(customizer);
			}
		}
		else {
			http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
			log.info("disabled formLogin");
		}

		http.csrf(ServerHttpSecurity.CsrfSpec::disable);
		http.cors(c -> c.configurationSource(request -> configProvider.getIfAvailable(DefaultCorsConfiguration::new)));

		for (AuthorizePathProvider provider : authorizePathProviders) {
			authorizePathConfig(http, provider.httpMethod(), provider.paths());
		}

		http.authorizeExchange(c -> c.anyExchange().permitAll());

		return http.build();
	}

	private void authorizePathConfig(ServerHttpSecurity http, @Nullable HttpMethod httpMethod, List<String> paths) {
		if (CollectionUtils.isEmpty(paths)) {
			return;
		}

		log.debug("authorizePathConfig[method: {}, paths: {}]", httpMethod, paths);

		if (httpMethod == null) {
			http.authorizeExchange(c -> c.pathMatchers(paths.toArray(new String[0])).authenticated());
		}
		else {
			http.authorizeExchange(c -> c.pathMatchers(httpMethod, paths.toArray(new String[0])).authenticated());
		}
	}

}
