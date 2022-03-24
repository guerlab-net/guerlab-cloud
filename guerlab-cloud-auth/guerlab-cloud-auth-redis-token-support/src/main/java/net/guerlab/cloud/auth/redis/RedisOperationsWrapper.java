/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

package net.guerlab.cloud.auth.redis;

import org.springframework.lang.Nullable;

/**
 * redis操作包装对象.
 *
 * @param <T>
 *         数据实体类型
 * @author guer
 */
public interface RedisOperationsWrapper<T> {

	/**
	 * 保存对象.
	 *
	 * @param key
	 *         key
	 * @param entity
	 *         对象
	 * @param timeout
	 *         超时时间
	 * @return 是否成功
	 */
	boolean put(String key, T entity, long timeout);

	/**
	 * 获取对象.
	 *
	 * @param key
	 *         key
	 * @return 对象
	 */
	@Nullable
	T get(String key);
}
