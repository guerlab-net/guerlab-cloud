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

package net.guerlab.cloud.loadbalancer.utils;

import java.util.List;

/**
 * 轮询器.
 *
 * @author guer
 */
public interface Poller<T> {

	/**
	 * Get next element selected by poller.
	 *
	 * @return next element
	 */
	T next();

	/**
	 * Update items stored in poller.
	 *
	 * @param items new item list
	 * @return new poller instance
	 */
	Poller<T> refresh(List<T> items);
}
