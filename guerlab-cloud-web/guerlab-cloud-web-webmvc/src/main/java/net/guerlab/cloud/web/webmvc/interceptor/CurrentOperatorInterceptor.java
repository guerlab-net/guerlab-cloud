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
package net.guerlab.cloud.web.webmvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.context.core.ContextAttributesHolder;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 当前操作者信息处理请求拦截器
 *
 * @author guer
 */
@Slf4j
public class CurrentOperatorInterceptor implements HandlerInterceptor, Ordered {

    /**
     * 默认排序
     */
    public static final int DEFAULT_ORDER = 0;

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String currentOperator = request.getHeader(Constants.CURRENT_OPERATOR_HEADER);
        log.debug("currentOperator: {}", currentOperator);
        if (currentOperator != null) {
            ContextAttributesHolder.get().put(Constants.CURRENT_OPERATOR, currentOperator);
        }
        return true;
    }
}