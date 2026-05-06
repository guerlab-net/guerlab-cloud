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

package net.guerlab.cloud.security.webmvc.autoconfigure;

import java.util.List;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;

import net.guerlab.cloud.security.core.AuthorizePathProvider;
import net.guerlab.cloud.security.core.autoconfigure.AuthorizePathAutoConfigure;
import net.guerlab.cloud.security.core.properties.AuthenticationTypeProperties;
import net.guerlab.cloud.security.core.properties.DefaultCorsConfiguration;

/**
 * WebMvc安全配置.
 *
 * @author guer
 */
@Slf4j
@Order(99)
@EnableWebSecurity
@AutoConfiguration(after = AuthorizePathAutoConfigure.class)
public class WebMvcSecurityAutoConfigure {

	private final ObjectProvider<CorsConfiguration> configProvider;

	private final ObjectProvider<AuthorizePathProvider> authorizePathProviders;

	/**
	 * 初始化WebMvc安全配置.
	 *
	 * @param configProvider         CorsConfiguration
	 * @param authorizePathProviders 授权路径提供者
	 */
	public WebMvcSecurityAutoConfigure(ObjectProvider<CorsConfiguration> configProvider,
			ObjectProvider<AuthorizePathProvider> authorizePathProviders) {
		this.configProvider = configProvider;
		this.authorizePathProviders = authorizePathProviders;
	}

	/**
	 * 构造SecurityFilterChain.
	 *
	 * @param http                           HttpSecurity
	 * @param authenticationTypeProperties   Http安全认证方式配置
	 * @param httpBasicConfigurerCustomizers httpBasic认证方式配置监听器列表
	 * @param formLoginConfigurerCustomizers 登录表单认证方式配置监听器列表
	 * @return SecurityFilterChain
	 * @throws Exception 配置异常时抛出错误
	 */
	@Bean
	public SecurityFilterChain securityWebFilterChain(
			HttpSecurity http,
			AuthenticationTypeProperties authenticationTypeProperties,
			ObjectProvider<Customizer<HttpBasicConfigurer<HttpSecurity>>> httpBasicConfigurerCustomizers,
			ObjectProvider<Customizer<FormLoginConfigurer<HttpSecurity>>> formLoginConfigurerCustomizers
	) throws Exception {
		if (authenticationTypeProperties.isEnableHttpBasic()) {
			http.httpBasic(Customizer.withDefaults());
			log.info("enabled httpBasic");
			for (Customizer<HttpBasicConfigurer<HttpSecurity>> customizer : httpBasicConfigurerCustomizers.stream()
					.toList()) {
				log.info("add httpBasic Configurer customizer: {}", customizer);
				http.httpBasic(customizer);
			}
		}
		else {
			http.httpBasic(AbstractHttpConfigurer::disable);
			log.info("disabled httpBasic");
		}

		if (authenticationTypeProperties.isEnableFormLogin()) {
			http.formLogin(Customizer.withDefaults());
			log.info("enabled formLogin");
			for (Customizer<FormLoginConfigurer<HttpSecurity>> customizer : formLoginConfigurerCustomizers.stream()
					.toList()) {
				log.info("add formLogin Configurer customizer: {}", customizer);
				http.formLogin(customizer);
			}
		}
		else {
			http.formLogin(AbstractHttpConfigurer::disable);
			log.info("disabled formLogin");
		}

		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(c -> c.configurationSource(request -> configProvider.getIfAvailable(DefaultCorsConfiguration::new)));

		for (AuthorizePathProvider provider : authorizePathProviders) {
			authorizePathConfig(http, provider.httpMethod(), provider.paths());
		}

		http.authorizeHttpRequests(c -> c.anyRequest().permitAll());

		return http.build();
	}

	private void authorizePathConfig(HttpSecurity http, @Nullable HttpMethod httpMethod, List<String> paths)
			throws Exception {
		if (CollectionUtils.isEmpty(paths)) {
			return;
		}

		log.debug("authorizePathConfig[method: {}, paths: {}]", httpMethod, paths);

		if (httpMethod == null) {
			http.authorizeHttpRequests(c -> c.requestMatchers((paths.toArray(new String[0]))).authenticated());
		}
		else {
			http.authorizeHttpRequests(c -> c.requestMatchers(httpMethod, paths.toArray(new String[0]))
					.authenticated());
		}
	}

}
