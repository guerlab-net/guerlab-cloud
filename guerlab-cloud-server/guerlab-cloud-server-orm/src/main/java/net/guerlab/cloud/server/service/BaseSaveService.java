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

package net.guerlab.cloud.server.service;

import java.util.Collection;

import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 基本保存服务接口.
 *
 * @param <T>
 *         数据类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
public interface BaseSaveService<T, SP extends SearchParams> extends QueryWrapperGetter<T, SP> {

	/**
	 * 添加.
	 *
	 * @param entity
	 *         实体
	 */
	void insert(T entity);

	/**
	 * 批量保存.
	 *
	 * @param collection
	 *         待保存列表
	 * @return 已保存列表W
	 */
	Collection<T> batchInsert(Collection<T> collection);

}
