/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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
 * 输入流配置.
 *
 * @author guer
 */
public class InputBindingDestinationAutoConfigure extends AbstractBindingDestinationAutoConfigure {

	private final Map<String, String> bindingDestinations;

	public InputBindingDestinationAutoConfigure(Map<String, String> bindingDestinations) {
		this.bindingDestinations = bindingDestinations;
	}

	@Override
	protected PutType putType() {
		return PutType.IN;
	}

	@Override
	protected Map<String, String> getBindingDestinations() {
		return bindingDestinations;
	}
}
