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

package net.guerlab.cloud.loadbalancer.utils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generic Poller.
 *
 * @author guer
 */
public class GenericPoller<T> implements Poller<T> {

	private final AtomicInteger index = new AtomicInteger(0);

	private final List<T> items;

	public GenericPoller(List<T> items) {
		this.items = items;
	}

	@Override
	public T next() {
		return items.get(Math.abs(index.getAndIncrement() % items.size()));
	}

	@Override
	public Poller<T> refresh(List<T> items) {
		return new GenericPoller<>(items);
	}
}
