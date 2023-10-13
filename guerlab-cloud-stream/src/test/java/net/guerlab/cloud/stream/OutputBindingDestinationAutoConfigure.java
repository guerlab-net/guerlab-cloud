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

/**
 * 输出流配置.
 *
 * @author guer
 */
public class OutputBindingDestinationAutoConfigure extends AbstractBindingDestinationAutoConfigure {

	private final Map<String, String> bindingDestinations;

	public OutputBindingDestinationAutoConfigure(Map<String, String> bindingDestinations) {
		this.bindingDestinations = bindingDestinations;
	}

	@Override
	protected PutType putType() {
		return PutType.OUT;
	}

	@Override
	protected Map<String, String> getBindingDestinations() {
		return bindingDestinations;
	}
}
