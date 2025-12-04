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

package net.guerlab.cloud.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import net.guerlab.cloud.api.headers.CurrentOperatorInterceptor;
import net.guerlab.cloud.loadbalancer.autoconfigure.GlobalLoadBalancerAutoConfiguration;

/**
 * 当前操作者信息处理请求拦截器自动配置.
 *
 * @author guer
 */
@Slf4j
@ConditionalOnClass(name = "net.guerlab.cloud.auth.context.AbstractContextHandler")
@AutoConfiguration(after = GlobalLoadBalancerAutoConfiguration.class)
public class CurrentOperatorInterceptorAutoConfigure {

	/**
	 * 构建当前操作者信息处理请求拦截器.
	 *
	 * @return 当前操作者信息处理请求拦截器
	 */
	@Bean
	public CurrentOperatorInterceptor currentOperatorInterceptor() {
		return new CurrentOperatorInterceptor();
	}
}
