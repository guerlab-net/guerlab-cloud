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

import net.guerlab.cloud.commons.api.SaveOrUpdate;
import net.guerlab.cloud.commons.entity.IBaseEntity;

/**
 * 基本新增或保存服务接口.
 *
 * @param <E> 数据类型
 * @author guer
 */
public interface BaseSaveOrUpdateService<E extends IBaseEntity> extends SaveOrUpdate<E> {

	/**
	 * 批量新增或保存.
	 *
	 * @param list 待保存列表
	 * @return 已保存列表
	 */
	default List<E> batchSaveOrUpdate(Collection<? extends E> list) {
		return batchSaveOrUpdate(list, false);
	}

	/**
	 * 批量新增或保存.
	 *
	 * @param list                       待保存列表
	 * @param ignoreBeforeCheckException 是否忽略前置检查异常
	 * @return 已保存列表
	 */
	List<E> batchSaveOrUpdate(Collection<? extends E> list, boolean ignoreBeforeCheckException);

}
