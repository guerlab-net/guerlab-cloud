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
package net.guerlab.cloud.auth.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.auth.annotation.IgnoreLogin;
import net.guerlab.cloud.auth.context.AbstractContextHandler;
import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * 抽象拦截器处理
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class AbstractHandlerFilter implements WebFilter, Ordered {

    /**
     * 默认排序
     */
    public static final int DEFAULT_ORDER = 0;

    private static final String[] METHODS = new String[] { "OPTIONS", "TRACE" };

    private static final String REAL_REQUEST_PATH = "realRequestPath";

    protected ResponseAdvisorProperties responseAdvisorProperties;

    protected RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * 获取注解
     *
     * @param handlerMethod
     *         处理方法
     * @param annotationClass
     *         注解类
     * @param <A>
     *         注解类
     * @return 注解对象
     */
    @SuppressWarnings("SameParameterValue")
    @Nullable
    protected static <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> annotationClass) {
        A annotation = handlerMethod.getMethodAnnotation(annotationClass);
        if (annotation == null) {
            Class<?> clazz = handlerMethod.getBeanType();
            annotation = clazz.getAnnotation(annotationClass);

            if (annotation == null) {
                annotation = clazz.getPackage().getAnnotation(annotationClass);
            }
        }
        return annotation;
    }

    private static boolean methodMatch(ServerHttpRequest request) {
        String requestMethod = request.getMethodValue();

        return Arrays.asList(METHODS).contains(requestMethod);
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

    @Override
    public final Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        //@formatter:off
        return requestMappingHandlerMapping.getHandler(exchange).ofType(HandlerMethod.class)
                .flatMap(handlerMethod -> {
                    preHandle(exchange.getRequest(), handlerMethod, exchange);
                    return chain.filter(exchange);
                })
                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()));
        //@formatter:on
    }

    protected final void preHandle(ServerHttpRequest request, HandlerMethod handlerMethod, ServerWebExchange exchange) {
        if (methodMatch(request) || uriMatch(request)) {
            return;
        }

        log.debug("intercept request[interceptor = {}, request = [{}]]", getClass(), parseRequestUri(request));

        boolean needLogin = getAnnotation(handlerMethod, IgnoreLogin.class) == null;

        log.debug("needLoginCheck[handler = {}, needLogin = {}]", handlerMethod, needLogin);

        if (needLogin) {
            String token = getToken(request, exchange);

            if (token != null) {
                AbstractContextHandler.setToken(token);
                preHandleWithToken(request, handlerMethod, token);
            } else {
                preHandleWithoutToken();
            }
        }
    }

    /**
     * 获取token
     *
     * @param request
     *         http请求对象
     * @return token
     */
    @Nullable
    private String getToken(ServerHttpRequest request, ServerWebExchange exchange) {
        ContextAttributes contextAttributes = (ContextAttributes) exchange.getAttributes().get(ContextAttributes.KEY);
        log.debug("contextAttributes: {}", contextAttributes);
        log.debug("test: {}", request.getMethodValue());
        AbstractContextHandler.setContextAttributes(contextAttributes);
        String token = AbstractContextHandler.getToken();

        if (token != null) {
            log.debug("get token by contextHandler[token = {}]", token);
            return token;
        }

        token = StringUtils.trimToNull(request.getHeaders().getFirst(Constants.TOKEN));
        if (token != null) {
            log.debug("get token by header[token = {}]", token);
            return token;
        }

        HttpCookie httpCookie = request.getCookies().getFirst(Constants.TOKEN);
        if (httpCookie != null) {
            token = StringUtils.trimToNull(httpCookie.getValue());
        }

        if (token != null) {
            log.debug("get token by cookie[token = {}]", token);
            return token;
        }

        token = StringUtils.trimToNull(request.getQueryParams().getFirst(Constants.TOKEN));

        if (token != null) {
            log.debug("get token by parameter[token = {}]", token);
        } else {
            log.debug("get token fail");
        }

        return token;
    }

    /**
     * 获取令牌成功前置处理
     *
     * @param request
     *         请求
     * @param handlerMethod
     *         处理方法
     * @param token
     *         令牌
     */
    protected void preHandleWithToken(ServerHttpRequest request, HandlerMethod handlerMethod, String token) {

    }

    /**
     * 获取令牌失败前置处理
     */
    protected void preHandleWithoutToken() {
        /*
         * 默认空处理
         */
    }

    private boolean uriMatch(ServerHttpRequest request) {
        String requestUri = parseRequestUri(request);
        return responseAdvisorProperties.getExcluded().stream().anyMatch(requestUri::startsWith);
    }

    private String parseRequestUri(ServerHttpRequest request) {
        String contextPath = StringUtils.trimToNull(request.getPath().contextPath().value());
        String requestUri = request.getPath().value();

        if (contextPath != null) {
            String newRequestUri = requestUri.replaceFirst(contextPath, "");
            log.debug("replace requestUri[form={}, to={}]", requestUri, newRequestUri);
            requestUri = newRequestUri;
        }

        return requestUri;
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setResponseAdvisorProperties(ResponseAdvisorProperties responseAdvisorProperties) {
        this.responseAdvisorProperties = responseAdvisorProperties;
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setRequestMappingHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }
}
