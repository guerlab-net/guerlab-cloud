/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.rsa.RsaKeys;
import net.guerlab.cloud.rsa.RsaUtils;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

/**
 * 请求体加密过滤器.
 *
 * @author guer
 */
@Slf4j
public class BodyEncryptFilter extends AbstractBodySecurityFilter {

	/**
	 * 创建实例.
	 *
	 * @param objectMapper ObjectMapper
	 * @param properties   请求体安全配置
	 */
	public BodyEncryptFilter(ObjectMapper objectMapper, BodySecurityProperties properties) {
		super(objectMapper, properties);
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		RsaKeys rsaKeys = getRsaKeys(exchange, false);
		if (rsaKeys == null || isNotJsonBody(exchange)) {
			return chain.filter(exchange);
		}

		return chain.filter(exchange.mutate().response(decorate(exchange, rsaKeys)).build());
	}

	private boolean isNotJsonBody(ServerWebExchange exchange) {
		return !MediaType.APPLICATION_JSON.isCompatibleWith(exchange.getResponse().getHeaders().getContentType());
	}

	private ServerHttpResponse decorate(ServerWebExchange exchange, RsaKeys rsaKeys) {
		return new CustomerServerHttpResponseDecorator(exchange, rsaKeys);
	}

	@Override
	public int getOrder() {
		return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 10;
	}

	private class CustomerServerHttpResponseDecorator extends ServerHttpResponseDecorator {

		private final ServerWebExchange exchange;

		private final RsaKeys rsaKeys;

		CustomerServerHttpResponseDecorator(ServerWebExchange exchange, RsaKeys rsaKeys) {
			super(exchange.getResponse());
			this.exchange = exchange;
			this.rsaKeys = rsaKeys;
		}

		@Override
		public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(HttpHeaders.CONTENT_TYPE, exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR));

			ClientResponse clientResponse = ClientResponse.create(
							Optional.ofNullable(exchange.getResponse().getStatusCode()).orElse(HttpStatus.OK))
					.headers(headers -> headers.putAll(httpHeaders)).body(Flux.from(body)).build();

			Mono<byte[]> modifiedBody = clientResponse.bodyToMono(byte[].class).flatMap(originalBody -> {
				try {
					byte[] encryptData = RsaUtils.encryptByPrivateKey(originalBody, rsaKeys.getPrivateKeyRef());

					BodyWrapper bodyWrapper = new BodyWrapper();
					bodyWrapper.setEncryptBody(Base64.getEncoder().encodeToString(encryptData));

					return Mono.just(objectMapper.writeValueAsBytes(bodyWrapper));
				}
				catch (Exception e) {
					return Mono.just(originalBody);
				}
			});

			BodyInserter<Mono<byte[]>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(
					modifiedBody, byte[].class);
			CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange,
					exchange.getResponse().getHeaders());
			return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
				Flux<DataBuffer> messageBody = outputMessage.getBody();
				HttpHeaders headers = getDelegate().getHeaders();
				if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
					messageBody = messageBody.doOnNext(data -> headers.setContentLength(data.readableByteCount()));
				}
				return getDelegate().writeWith(messageBody);
			}));
		}

		@Override
		public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
			return writeWith(Flux.from(body).flatMapSequential(p -> p));
		}
	}
}
