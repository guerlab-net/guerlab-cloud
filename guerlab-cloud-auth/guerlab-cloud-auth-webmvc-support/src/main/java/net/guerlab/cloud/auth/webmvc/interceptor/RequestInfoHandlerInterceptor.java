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
package net.guerlab.cloud.auth.webmvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.auth.AbstractContextHandler;
import net.guerlab.cloud.commons.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求信息拦截器
 *
 * @author guer
 */
@Slf4j
public class RequestInfoHandlerInterceptor implements HandlerInterceptor, Ordered {

    @Override
    public int getOrder() {
        return AbstractHandlerInterceptor.DEFAULT_ORDER - 20;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        AbstractContextHandler.setRequestMethod(request.getMethod());
        AbstractContextHandler.setRequestUri(parseRequestUri(request));
        AbstractContextHandler
                .setCompleteRequestUri(StringUtils.trimToNull(request.getHeader(Constants.COMPLETE_REQUEST_URI)));
        log.debug("save request info to AbstractContextHandler");
        return true;
    }

    private String parseRequestUri(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();

        if (contextPath != null) {
            String newRequestUri = requestUri.replaceFirst(contextPath, "");
            log.debug("replace requestUri[form={}, to={}]", requestUri, newRequestUri);
            requestUri = newRequestUri;
        }

        return requestUri;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) {
        AbstractContextHandler.clean();
        log.debug("invoke AbstractContextHandler.clean()");
    }

}
