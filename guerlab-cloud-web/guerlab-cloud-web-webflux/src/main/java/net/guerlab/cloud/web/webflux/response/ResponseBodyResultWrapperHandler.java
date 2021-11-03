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
package net.guerlab.cloud.web.webflux.response;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.web.core.response.ResponseBodyWrapperSupport;
import net.guerlab.cloud.web.webflux.utils.RequestUtils;
import net.guerlab.web.result.Result;
import net.guerlab.web.result.Succeed;
import org.springframework.core.MethodParameter;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 响应内容结果包装处理
 *
 * @author guer
 */
@Slf4j
public class ResponseBodyResultWrapperHandler extends ResponseBodyResultHandler {

    private static final MethodParameter METHOD_PARAMETER_WITH_MONO_RESULT;

    static {
        try {
            METHOD_PARAMETER_WITH_MONO_RESULT = new MethodParameter(ResponseBodyResultWrapperHandler.class.getDeclaredMethod("methodForParams"), -1);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private final ResponseBodyWrapperSupport support;

    public ResponseBodyResultWrapperHandler(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver,
            ResponseBodyWrapperSupport support) {
        super(writers, resolver);
        this.support = support;
        setOrder(-10);
    }

    @SuppressWarnings("SameReturnValue")
    @Nullable
    private static Mono<Result<?>> methodForParams() {
        return null;
    }

    @Override
    public boolean supports(HandlerResult result) {
        return support.notHasAnnotation(result.getReturnTypeSource());
    }

    @Override
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        Object body = result.getReturnValue();
        MethodParameter bodyTypeParameter = result.getReturnTypeSource();
        if (body == null) {
            log.debug("wrapper with null body");
            return writeBody(new Succeed<>(), METHOD_PARAMETER_WITH_MONO_RESULT, exchange);
        } else if (support.noConvertObject(body)) {
            log.debug("un wrapper with noConvertObject, body class is {}", body.getClass());
            return writeBody(body, bodyTypeParameter, exchange);
        } else if (matchExcluded(exchange, bodyTypeParameter.getMethod())) {
            log.debug("un wrapper with matchExcluded");
            return writeBody(body, bodyTypeParameter, exchange);
        }

        Mono<?> mono = null;
        if (body instanceof Mono) {
            mono = (Mono<?>) body;
            log.debug("un wrapper with Mono");
        } else if (body instanceof Flux) {
            Flux<?> flux = (Flux<?>) body;
            mono = flux.collectList();
            log.debug("un wrapper with Flux");
        }

        if (mono != null) {
            body = mono.map(Succeed::new).defaultIfEmpty(new Succeed<>());
        } else {
            body = new Succeed<>(body);
        }

        return writeBody(body, METHOD_PARAMETER_WITH_MONO_RESULT, exchange);
    }

    private boolean matchExcluded(ServerWebExchange exchange, @Nullable Method method) {
        return support.matchExcluded(getRequestPath(exchange), method);
    }

    private String getRequestPath(ServerWebExchange exchange) {
        return RequestUtils.parseRequestUri(exchange.getRequest());
    }
}
