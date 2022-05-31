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

package net.guerlab.cloud.web.webflux.autoconfigure;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;

import net.guerlab.cloud.web.core.response.ResponseBodyWrapperSupport;
import net.guerlab.cloud.web.webflux.response.ResponseBodyResultWrapperHandler;

/**
 * 响应数据处理自动配置.
 *
 * @author guer
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.web", name = "wrapper-response", havingValue = "true", matchIfMissing = true)
public class WebFluxResponseAdvisorAutoConfigure {

	/**
	 * 构建响应内容结果包装处理.
	 *
	 * @param serverCodecConfigurer        ServerCodecConfigurer
	 * @param requestedContentTypeResolver RequestedContentTypeResolver
	 * @param support                      响应对象包装支持
	 * @return 响应内容结果包装处理
	 */
	@Bean
	public ResponseBodyResultWrapperHandler responseBodyResultWrapperHandler(
			ServerCodecConfigurer serverCodecConfigurer, RequestedContentTypeResolver requestedContentTypeResolver,
			ResponseBodyWrapperSupport support) {
		return new ResponseBodyResultWrapperHandler(serverCodecConfigurer.getWriters(), requestedContentTypeResolver,
				support);
	}
}
