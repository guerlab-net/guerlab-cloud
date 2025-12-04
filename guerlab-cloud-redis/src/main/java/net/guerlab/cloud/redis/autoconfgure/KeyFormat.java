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

package net.guerlab.cloud.redis.autoconfgure;

/**
 * key格式化.
 *
 * @author guer
 */
public interface KeyFormat {

	/**
	 * 支持对象类型.
	 *
	 * @return 对象类型
	 */
	Class<?> targetClass();

	/**
	 * key格式化.
	 *
	 * @param key 源key
	 * @return 格式化后的key
	 */
	Object format(Object key);
}
