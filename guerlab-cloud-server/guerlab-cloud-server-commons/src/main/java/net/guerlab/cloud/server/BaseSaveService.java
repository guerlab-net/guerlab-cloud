/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

import net.guerlab.cloud.commons.api.Insert;

/**
 * 基本保存服务接口.
 *
 * @param <E> 数据类型
 * @author guer
 */
public interface BaseSaveService<E> extends Insert<E> {

	/**
	 * 批量保存.
	 *
	 * @param collection 待保存列表
	 * @return 已保存列表W
	 */
	List<E> batchInsert(Collection<? extends E> collection);

}
