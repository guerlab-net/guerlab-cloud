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

package net.guerlab.cloud.server.service.orm;

import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.BaseService;

/**
 * 基本ORM服务接口.
 *
 * @param <E> 数据类型
 * @param <Q> 搜索参数类型
 * @author guer
 */
public interface BaseOrmService<E extends IBaseEntity, Q extends SearchParams>
		extends BaseOrmFindService<E, Q>, BaseOrmSaveService<E, Q>,
		BaseOrmUpdateService<E, Q>, BaseOrmSaveOrUpdateService<E>,
		BaseOrmDeleteService<E, Q>, BaseService<E, Q>,
		QueryWrapperGetter<E, Q> { }
