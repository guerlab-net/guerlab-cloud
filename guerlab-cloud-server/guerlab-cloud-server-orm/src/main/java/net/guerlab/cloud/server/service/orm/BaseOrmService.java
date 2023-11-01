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

package net.guerlab.cloud.server.service.orm;

import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.BaseService;

/**
 * 基本ORM服务接口.
 *
 * @param <E>  数据类型
 * @param <SP> 搜索参数类型
 * @author guer
 */
public interface BaseOrmService<E, SP extends SearchParams>
		extends BaseOrmFindService<E, SP>, BaseOrmSaveService<E, SP>, BaseOrmUpdateService<E, SP>, BaseOrmDeleteService<E, SP>, BaseService<E, SP>, QueryWrapperGetter<E, SP> { }
