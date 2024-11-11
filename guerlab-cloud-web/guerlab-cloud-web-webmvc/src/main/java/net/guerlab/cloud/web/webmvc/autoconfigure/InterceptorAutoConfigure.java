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

package net.guerlab.cloud.web.webmvc.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.guerlab.cloud.web.webmvc.interceptor.ContextAttributesHolderCleanInterceptor;
import net.guerlab.cloud.web.webmvc.interceptor.CurrentOperatorInterceptor;
import net.guerlab.cloud.web.webmvc.interceptor.TransferHeaderInterceptor;

/**
 * 拦截器自动配置.
 *
 * @author guer
 */
@AutoConfiguration
public class InterceptorAutoConfigure implements WebMvcConfigurer {

	@Override
	public final void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ContextAttributesHolderCleanInterceptor()).order(Ordered.HIGHEST_PRECEDENCE);
		registry.addInterceptor(new TransferHeaderInterceptor()).order(Ordered.HIGHEST_PRECEDENCE + 1);
		registry.addInterceptor(new CurrentOperatorInterceptor()).order(Ordered.HIGHEST_PRECEDENCE + 2);
	}
}
