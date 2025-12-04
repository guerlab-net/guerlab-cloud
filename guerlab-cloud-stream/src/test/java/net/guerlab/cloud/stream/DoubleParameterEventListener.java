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

package net.guerlab.cloud.stream;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author guer
 */
@Slf4j
@Component
public class DoubleParameterEventListener {

	@EventListener(DoubleParameterEvent.class)
	public void onEvent(DoubleParameterEvent event) {
		log.debug("onEvent: {}", event);
		throw new DynamicsFunctionTestException();
	}
}
