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

import org.springframework.context.ApplicationEvent;

/**
 * @author guer
 */
public class SimpleEvent extends ApplicationEvent {

	private final String input;

	public SimpleEvent(Object source, String input) {
		super(source);
		this.input = input;
	}

	@Override
	public String toString() {
		return "SimpleEvent{input='" + input + "'}";
	}
}
