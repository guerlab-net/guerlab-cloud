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

package net.guerlab.cloud.gateway.core.bodysecurity;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.gateway.core.exception.ErrorAttributesWrapper;

/**
 * 请求体安全自动配置.
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(BodySecurityProperties.class)
public class BodySecurityAutoConfigure {

	/**
	 * 构造请求体解密过滤器.
	 *
	 * @param objectMapper objectMapper
	 * @param properties   请求体安全配置
	 * @return 请求体解密过滤器
	 */
	@Bean
	public BodyDecryptFilter bodyDecryptFilter(ObjectMapper objectMapper, BodySecurityProperties properties) {
		return new BodyDecryptFilter(objectMapper, properties);
	}

	/**
	 * 构造请求体加密过滤器.
	 *
	 * @param objectMapper objectMapper
	 * @param properties   请求体安全配置
	 * @return 请求体加密过滤器
	 */
	@Bean
	public BodyEncryptFilter bodyEncryptFilter(ObjectMapper objectMapper, BodySecurityProperties properties) {
		return new BodyEncryptFilter(objectMapper, properties);
	}

	/**
	 * 构造内容加密错误信息包装处理器.
	 *
	 * @param objectMapper objectMapper
	 * @param properties   请求体安全配置
	 * @return 内容加密错误信息包装处理器
	 */
	@Bean
	public ErrorAttributesWrapper bodyEncryptErrorAttributesWrapper(ObjectMapper objectMapper,
			BodySecurityProperties properties) {
		return new BodyEncryptErrorAttributesWrapper(objectMapper, properties);
	}
}
