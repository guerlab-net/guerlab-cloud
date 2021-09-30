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
package net.guerlab.cloud.auth.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.auth.web.properties.AuthWebProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 抽象token处理
 *
 * @param <A>
 *         授权配置类型
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class AbstractTokenHandlerInterceptor<A extends AuthWebProperties> extends AbstractHandlerInterceptor {

    /**
     * 授权配置
     */
    protected A authProperties;

    @Override
    public int getOrder() {
        return DEFAULT_ORDER - 10;
    }

    @Override
    protected void preHandleWithToken(HttpServletRequest request, HandlerMethod handlerMethod, String token) {
        boolean accept = accept(token, request);

        log.debug("token preHandler[instance = {}, accept = {}, token = {}]", getClass(), accept, token);

        if (accept) {
            setTokenInfo(token);
        }
    }

    /**
     * 判断是否处理该token
     *
     * @param token
     *         token
     * @param request
     *         请求对象
     * @return 是否处理该token
     */
    protected abstract boolean accept(String token, HttpServletRequest request);

    /**
     * 设置Token信息
     *
     * @param token
     *         token
     */
    protected abstract void setTokenInfo(String token);

    /**
     * 获取授权配置
     *
     * @return 授权配置
     */
    public A getAuthProperties() {
        return authProperties;
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setAuthProperties(A authProperties) {
        this.authProperties = authProperties;
    }
}
