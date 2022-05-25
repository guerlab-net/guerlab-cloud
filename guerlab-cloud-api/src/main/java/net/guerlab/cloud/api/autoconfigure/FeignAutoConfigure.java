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

package net.guerlab.cloud.api.autoconfigure;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.api.feign.ErrorDecoderChain;
import net.guerlab.cloud.api.feign.FailResponseDecoder;
import net.guerlab.cloud.api.feign.OrderedErrorDecoder;
import net.guerlab.cloud.api.feign.ResultDecoder;

/**
 * feign自动配置.
 *
 * @author guer
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class FeignAutoConfigure {

	/**
	 * 构造结果解析.
	 *
	 * @param objectMapper objectMapper
	 * @return 结果解析
	 */
	@Bean
	public ResultDecoder resultDecoder(ObjectMapper objectMapper) {
		return new ResultDecoder(objectMapper);
	}

	/**
	 * 失败响应解析.
	 *
	 * @param objectMapper objectMapper
	 * @return 失败响应解析
	 */
	@Bean
	public FailResponseDecoder failResponseDecoder(ObjectMapper objectMapper) {
		return new FailResponseDecoder(objectMapper);
	}

	/**
	 * 构造错误解析器链.
	 *
	 * @param decoders 错误解析器
	 * @return 错误解析器链
	 */
	@Bean
	public ErrorDecoderChain errorDecoderChain(List<OrderedErrorDecoder> decoders) {
		ErrorDecoderChain chain = new ErrorDecoderChain();
		chain.setDecoders(decoders);
		return chain;
	}

}
