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

package net.guerlab.cloud.web.webmvc.autoconfigure;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import net.guerlab.cloud.core.autoconfigure.ObjectMapperAutoConfigure;
import net.guerlab.cloud.security.core.properties.CorsProperties;
import net.guerlab.commons.collection.CollectionUtil;

/**
 * web mvc配置.
 *
 * @author guer
 */
@AutoConfiguration(after = ObjectMapperAutoConfigure.class)
@EnableConfigurationProperties(CorsProperties.class)
public class WebMvcAutoConfigure {

	/**
	 * webmvc自动配置.
	 *
	 * @author guer
	 */
	@AutoConfiguration
	@ConditionalOnClass(WebMvcConfigurer.class)
	public static class MvcAutoConfigure implements WebMvcConfigurer {

		private final ObjectMapper objectMapper;

		private final LocaleChangeInterceptor localeChangeInterceptor;

		/**
		 * 初始化webmvc自动配置.
		 *
		 * @param objectMapper            objectMapper
		 * @param localeChangeInterceptor localeChangeInterceptor
		 */
		public MvcAutoConfigure(ObjectMapper objectMapper, LocaleChangeInterceptor localeChangeInterceptor) {
			this.objectMapper = objectMapper;
			this.localeChangeInterceptor = localeChangeInterceptor;
		}

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(localeChangeInterceptor);
		}

		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
			if (CollectionUtil.isEmpty(converters)) {
				converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
				converters.add(new StringHttpMessageConverter());
				return;
			}

			for (HttpMessageConverter<?> converter : converters) {
				if (converter instanceof MappingJackson2XmlHttpMessageConverter) {
					continue;
				}
				if (converter instanceof AbstractJackson2HttpMessageConverter messageConverter) {
					messageConverter.setObjectMapper(objectMapper);
				}
			}
		}
	}
}
