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

package net.guerlab.cloud.commons.api.feign;

import net.guerlab.cloud.commons.api.ManageApi;
import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 管理Feign定义.
 *
 * @param <E>  返回实体类型
 * @param <SP> 搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface ManageFeign<E extends IBaseEntity, SP extends SearchParams> extends QueryFeign<E, SP>, ManageApi<E, SP> { }
