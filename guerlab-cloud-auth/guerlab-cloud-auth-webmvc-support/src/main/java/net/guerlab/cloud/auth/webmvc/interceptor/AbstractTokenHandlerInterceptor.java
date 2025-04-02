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

package net.guerlab.cloud.auth.webmvc.interceptor;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.method.HandlerMethod;

import net.guerlab.cloud.auth.web.properties.AuthWebProperties;
import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;

/**
 * 抽象token处理.
 *
 * @param <A> 授权配置类型
 * @author guer
 */
@Getter
@SuppressWarnings("unused")
@Slf4j
public abstract class AbstractTokenHandlerInterceptor<A extends AuthWebProperties> extends AbstractHandlerInterceptor {

	/**
	 * 授权配置.
	 */
	protected final A authProperties;

	/**
	 * 创建.
	 *
	 * @param responseAdvisorProperties http响应数据处理配置参数
	 * @param authProperties            授权配置
	 */
	protected AbstractTokenHandlerInterceptor(ResponseAdvisorProperties responseAdvisorProperties, A authProperties) {
		super(responseAdvisorProperties);
		this.authProperties = authProperties;
	}

	@Override
	public int getOrder() {
		return DEFAULT_ORDER - 10;
	}

	@Override
	protected void preHandleWithToken(HttpServletRequest request, HandlerMethod handlerMethod, String token, List<Class<?>> targetAuthTypes) {
		boolean accept = accept(token, request, targetAuthTypes);

		log.debug("token preHandler[instance = {}, accept = {}, token = {}]", getClass(), accept, token);

		if (accept) {
			setTokenInfo(token);
		}
	}

	/**
	 * 判断是否处理该token.
	 *
	 * @param token           token
	 * @param request         请求对象
	 * @param targetAuthTypes 目标认证类型列表
	 * @return 是否处理该token
	 */
	protected abstract boolean accept(String token, HttpServletRequest request, List<Class<?>> targetAuthTypes);

	/**
	 * 设置Token信息.
	 *
	 * @param token token
	 */
	protected abstract void setTokenInfo(String token);

}
