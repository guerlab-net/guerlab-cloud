package net.guerlab.smart.platform.basic.gateway;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * Cors处理拦截器
 *
 * @author guer
 */
@SuppressWarnings("NullableProblems")
@Component
public class CorsFilter implements WebFilter {

    private static final String ALL = "*";

    private static final long MAX_AGE = 18000L;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (!CorsUtils.isCorsRequest(request)) {
            return chain.filter(exchange);
        }

        ServerHttpResponse response = exchange.getResponse();

        if (request.getMethod() != HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        HttpHeaders requestHeaders = request.getHeaders();

        HttpHeaders headers = response.getHeaders();
        headers.setAccessControlAllowOrigin(requestHeaders.getOrigin());
        headers.setAccessControlAllowHeaders(requestHeaders.getAccessControlRequestHeaders());
        headers.setAccessControlAllowCredentials(true);
        headers.setAccessControlAllowMethods(Arrays.asList(HttpMethod.values()));
        headers.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
        headers.setAccessControlMaxAge(MAX_AGE);
        response.setStatusCode(HttpStatus.OK);
        return Mono.empty();
    }

}
