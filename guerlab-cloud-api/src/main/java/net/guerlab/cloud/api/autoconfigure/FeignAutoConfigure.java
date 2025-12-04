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

package net.guerlab.cloud.api.autoconfigure;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import feign.optionals.OptionalDecoder;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import net.guerlab.cloud.api.feign.DecoderWrapper;
import net.guerlab.cloud.api.feign.ErrorDecoderChain;
import net.guerlab.cloud.api.feign.FailResponseDecoder;
import net.guerlab.cloud.api.feign.FeignBuilderInterceptor;
import net.guerlab.cloud.api.feign.JsonDecoder;
import net.guerlab.cloud.api.feign.LoadbalancerNotContainInstanceResponseDecoder;
import net.guerlab.cloud.api.feign.OrderedErrorDecoder;
import net.guerlab.cloud.api.feign.TypeDecoder;

/**
 * feign自动配置.
 *
 * @author guer
 */
@Slf4j
@AutoConfiguration
public class FeignAutoConfigure {

	@Autowired
	private ObjectFactory<HttpMessageConverters> messageConverters;

	/**
	 * 构建Feign.Builder构建拦截器.
	 *
	 * @return Feign.Builder构建拦截器.
	 */
	@Bean
	public FeignBuilderInterceptor feignBuilderInterceptor() {
		return new FeignBuilderInterceptor();
	}

	/**
	 * 构建解析包装器.
	 *
	 * @param typeDecoderObjectProvider typeDecoderObjectProvider
	 * @param customizers               customizers
	 * @return 解析包装器
	 */
	@Bean
	@Primary
	public DecoderWrapper decoderWrapper(
			ObjectProvider<TypeDecoder> typeDecoderObjectProvider,
			ObjectProvider<HttpMessageConverterCustomizer> customizers
	) {
		Decoder defaultDecoder = new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(messageConverters, customizers)));
		return new DecoderWrapper(defaultDecoder, typeDecoderObjectProvider);
	}

	/**
	 * 构建json类型解析器.
	 *
	 * @param objectMapper objectMapper
	 * @return json类型解析器
	 */
	@Bean
	public JsonDecoder jsonDecoder(ObjectMapper objectMapper) {
		return new JsonDecoder(objectMapper);
	}

	/**
	 * 负载均衡未发现实例失败响应解析.
	 *
	 * @return 负载均衡未发现实例失败响应解析
	 */
	@Bean
	public LoadbalancerNotContainInstanceResponseDecoder loadbalancerNotContainInstanceResponseDecoder() {
		return new LoadbalancerNotContainInstanceResponseDecoder();
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
