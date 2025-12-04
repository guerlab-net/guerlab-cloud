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

package net.guerlab.cloud.gateway.bodysecurity;

import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.rsa.RsaKeys;
import net.guerlab.cloud.rsa.RsaUtils;

/**
 * 请求体解密过滤器.
 *
 * @author guer
 */
@Slf4j
public class BodyDecryptFilter extends AbstractBodySecurityFilter {

	/**
	 * 创建实例.
	 *
	 * @param objectMapper ObjectMapper
	 * @param properties   请求体安全配置
	 */
	public BodyDecryptFilter(ObjectMapper objectMapper, BodySecurityProperties properties) {
		super(objectMapper, properties);
	}

	private boolean isNotPostOrPutRequest(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		return request.getMethod() != HttpMethod.POST && request.getMethod() != HttpMethod.PUT;
	}

	private boolean isNotJsonBody(ServerWebExchange exchange) {
		return !MediaType.APPLICATION_JSON.isCompatibleWith(exchange.getRequest().getHeaders().getContentType());
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		RsaKeys rsaKeys = getRsaKeys(exchange, true);
		if (rsaKeys == null || isNotPostOrPutRequest(exchange) || isNotJsonBody(exchange)) {
			return chain.filter(exchange);
		}

		ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());

		Mono<byte[]> modifiedBody = serverRequest.bodyToMono(byte[].class).flatMap(body -> {
			try {
				String encryptBody = objectMapper.readValue(body, BodyWrapper.class).getEncryptBody();
				return Mono.just(RsaUtils.decryptByPrivateKey(Base64.getDecoder()
						.decode(encryptBody), rsaKeys.getPrivateKeyRef()));
			}
			catch (Exception e) {
				Exception ex = new ResponseStatusException(HttpStatus.BAD_REQUEST, "request body decrypt fail");
				return Mono.error(ex);
			}
		});

		BodyInserter<Mono<byte[]>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody,
				byte[].class);
		HttpHeaders headers = new HttpHeaders();
		headers.putAll(exchange.getRequest().getHeaders());
		headers.remove(HttpHeaders.CONTENT_LENGTH);

		CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
		return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
			ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {

				@Override
				public HttpHeaders getHeaders() {
					long contentLength = headers.getContentLength();
					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.putAll(super.getHeaders());
					if (contentLength > 0) {
						httpHeaders.setContentLength(contentLength);
					}
					else {
						httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
					}
					return httpHeaders;
				}

				@Override
				public Flux<DataBuffer> getBody() {
					return outputMessage.getBody();
				}
			};

			return chain.filter(exchange.mutate().request(decorator).build());
		}));
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 100;
	}
}
