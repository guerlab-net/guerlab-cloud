/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.auth.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.auth.AbstractContextHandler;
import net.guerlab.cloud.commons.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 请求信息拦截器
 *
 * @author guer
 */
@Slf4j
public class RequestInfoHandlerFilter implements WebFilter, Ordered {

    @Override
    public int getOrder() {
        return AbstractHandlerFilter.DEFAULT_ORDER - 20;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        AbstractContextHandler.setRequestMethod(request.getMethodValue());
        AbstractContextHandler.setRequestUri(parseRequestUri(request));
        AbstractContextHandler.setCompleteRequestUri(
                StringUtils.trimToEmpty(request.getHeaders().getFirst(Constants.COMPLETE_REQUEST_URI)));
        log.debug("save request info to AbstractContextHandler");

        return chain.filter(exchange).then(Mono.fromCallable(() -> {
            AbstractContextHandler.clean();
            log.debug("invoke AbstractContextHandler.clean()");
            return null;
        }));
    }

    private String parseRequestUri(ServerHttpRequest request) {
        RequestPath requestPath = request.getPath();
        String contextPath = StringUtils.trimToNull(requestPath.contextPath().value());
        String requestUri = requestPath.value();

        if (contextPath != null) {
            String newRequestUri = requestUri.replaceFirst(contextPath, "");
            log.debug("replace requestUri[form={}, to={}]", requestUri, newRequestUri);
            requestUri = newRequestUri;
        }

        return requestUri;
    }
}
