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

package net.guerlab.cloud.geo.i18n;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import net.guerlab.cloud.commons.i18n.MultiMessageSourceAwareAutoConfigure;
import net.guerlab.cloud.commons.i18n.MultiMessageSourceProvider;

/**
 * 地理信息多消息源处理提供者自动配置.
 *
 * @author guer
 */
@AutoConfiguration(before = MultiMessageSourceAwareAutoConfigure.class)
public class GeoMultiMessageSourceProviderAutoConfigure {

	/**
	 * 地理信息多消息源处理提供者.
	 *
	 * @return 地理信息多消息源处理提供者
	 */
	@Bean
	public MultiMessageSourceProvider geoMultiMessageSourceProvider() {
		return () -> "messages/geo";
	}
}
