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

package net.guerlab.cloud.commons.i18n;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 公共多消息源处理提供者自动配置.
 *
 * @author guer
 */
@AutoConfiguration(before = MultiMessageSourceAwareAutoConfigure.class)
public class CommonsMultiMessageSourceProviderAutoConfigure {

	/**
	 * 公共多消息源处理提供者.
	 *
	 * @return 公共多消息源处理提供者
	 */
	@Bean
	public MultiMessageSourceProvider commonsMultiMessageSourceProvider() {
		return () -> "messages/commons";
	}
}
