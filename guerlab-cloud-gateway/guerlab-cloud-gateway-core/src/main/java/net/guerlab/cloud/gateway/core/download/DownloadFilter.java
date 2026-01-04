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

package net.guerlab.cloud.gateway.core.download;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.gateway.core.base.CorsProperties;

/**
 * 下载扩展支持.
 *
 * @author guer
 */
@Slf4j
public class DownloadFilter implements GatewayFilter, GlobalFilter, Ordered {

	private final CorsProperties properties;

	public DownloadFilter(CorsProperties properties) {
		this.properties = properties;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return chain.filter(exchange.mutate().response(decorate(exchange)).build());
	}

	private ServerHttpResponse decorate(ServerWebExchange exchange) {
		return new CustomerServerHttpResponseDecorator(exchange);
	}

	@Override
	public int getOrder() {
		return 0;
	}

	private class CustomerServerHttpResponseDecorator extends ServerHttpResponseDecorator {

		CustomerServerHttpResponseDecorator(ServerWebExchange exchange) {
			super(exchange.getResponse());
		}

		@Override
		public HttpHeaders getHeaders() {
			HttpHeaders headers = super.getHeaders();

			ContentDisposition disposition = headers.getContentDisposition();
			if (!disposition.isAttachment()) {
				return headers;
			}

			List<String> accessControlExposeHeaders = headers.getAccessControlExposeHeaders();
			if (accessControlExposeHeaders.contains(HttpHeaders.CONTENT_DISPOSITION)) {
				return headers;
			}

			List<String> newAccessControlExposeHeaders = new ArrayList<>(properties.getAccessControlExposeHeaders());
			newAccessControlExposeHeaders.add(HttpHeaders.CONTENT_DISPOSITION);

			newAccessControlExposeHeaders = newAccessControlExposeHeaders.stream().distinct().toList();

			if (!newAccessControlExposeHeaders.isEmpty()) {
				headers.setAccessControlExposeHeaders(newAccessControlExposeHeaders);
			}

			return headers;
		}
	}
}
