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

package net.guerlab.cloud.commons.api;

import net.guerlab.cloud.searchparams.SearchParams;

/**
 * APi定义.
 *
 * @param <E>  返回实体类型
 * @param <SP> 搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface Api<E, SP extends SearchParams> extends SelectById<E, SP>, SelectOne<E, SP>, SelectList<E, SP>, SelectPage<E, SP>, SelectCount<SP>,
		Insert<E>, UpdateById<E>, DeleteById, Delete<SP> { }
