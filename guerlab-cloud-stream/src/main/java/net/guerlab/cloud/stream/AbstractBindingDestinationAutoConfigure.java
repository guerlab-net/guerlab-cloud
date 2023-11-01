/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.stream;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;

/**
 * Binding目的地自动配置.
 *
 * @author guer
 */
public abstract class AbstractBindingDestinationAutoConfigure {

	/**
	 * 初始化binding目的地.
	 *
	 * @param bindingServiceProperties bindingServiceProperties
	 */
	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	public void initBindingDestination(BindingServiceProperties bindingServiceProperties) {
		Map<String, BindingProperties> bindings = bindingServiceProperties.getBindings();

		Map<String, String> bindingDestinations = getBindingDestinations();

		for (Map.Entry<String, String> entry : bindingDestinations.entrySet()) {
			String bindingName = putType().formatName(entry.getKey());
			if (bindings.containsKey(bindingName)) {
				continue;
			}

			BindingProperties bindingProperties = new BindingProperties();
			bindingProperties.setDestination(entry.getValue());

			bindings.put(bindingName, bindingProperties);
		}
	}

	/**
	 * 获取推送类型.
	 *
	 * @return 推送类型
	 */
	protected abstract PutType putType();

	/**
	 * 获取binding目的地列表.
	 *
	 * @return binding目的地列表
	 */
	protected abstract Map<String, String> getBindingDestinations();

}
