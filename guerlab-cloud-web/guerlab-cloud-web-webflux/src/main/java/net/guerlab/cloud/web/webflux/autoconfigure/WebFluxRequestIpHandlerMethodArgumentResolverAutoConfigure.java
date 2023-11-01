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

package net.guerlab.cloud.web.webflux.autoconfigure;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

import net.guerlab.cloud.web.webflux.support.WebFluxRequestIpHandlerMethodArgumentResolver;

/**
 * 构造请求IP参数处理参数解析自动配置.
 *
 * @author guer
 */
@Slf4j
@AutoConfiguration
public class WebFluxRequestIpHandlerMethodArgumentResolverAutoConfigure implements WebFluxConfigurer {

	@Override
	public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
		configurer.addCustomResolver(new WebFluxRequestIpHandlerMethodArgumentResolver());
		log.debug("add WebFluxRequestIpHandlerMethodArgumentResolver");
	}
}

