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

package net.guerlab.cloud.server;

import java.util.Collection;
import java.util.List;

import net.guerlab.cloud.commons.api.UpdateById;
import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 基本更新服务接口.
 *
 * @param <E> 数据类型
 * @param <Q> 搜索参数类型
 * @author guer
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface BaseUpdateService<E extends IBaseEntity, Q extends SearchParams> extends UpdateById<E> {

	/**
	 * 根据条件更新.
	 *
	 * @param entity       实体
	 * @param searchParams 搜索条件
	 * @return 是否更新成功
	 */
	boolean update(E entity, Q searchParams);

	/**
	 * 批量更新.
	 *
	 * @param collection 待更新列表
	 * @return 已更新列表
	 */
	default List<E> batchUpdateById(Collection<? extends E> collection) {
		return batchUpdateById(collection, false);
	}

	/**
	 * 批量更新.
	 *
	 * @param collection                 待更新列表
	 * @param ignoreBeforeCheckException 是否忽略前置检查异常
	 * @return 已更新列表
	 */
	List<E> batchUpdateById(Collection<? extends E> collection, boolean ignoreBeforeCheckException);

}
