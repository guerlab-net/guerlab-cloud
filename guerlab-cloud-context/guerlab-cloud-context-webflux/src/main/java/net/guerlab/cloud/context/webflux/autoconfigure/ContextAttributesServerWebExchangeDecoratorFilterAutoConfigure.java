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

package net.guerlab.cloud.context.webflux.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.context.webflux.filter.ContextAttributesServerWebExchangeDecoratorFilter;

/**
 * 上下文属性包装器处理自动配置.
 *
 * @author guer
 */
@Configuration
public class ContextAttributesServerWebExchangeDecoratorFilterAutoConfigure {

	/**
	 * 创建上下文属性包装器处理.
	 *
	 * @return 上下文属性包装器处理
	 */
	@Bean
	public ContextAttributesServerWebExchangeDecoratorFilter contextAttributesServerWebExchangeDecoratorFilter() {
		return new ContextAttributesServerWebExchangeDecoratorFilter();
	}
}
