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

package net.guerlab.cloud.server;

import net.guerlab.cloud.commons.api.SelectById;
import net.guerlab.cloud.commons.api.SelectByIds;
import net.guerlab.cloud.commons.api.SelectCount;
import net.guerlab.cloud.commons.api.SelectList;
import net.guerlab.cloud.commons.api.SelectOne;
import net.guerlab.cloud.commons.api.SelectPage;
import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 基本查询服务接口.
 *
 * @param <E> 数据类型
 * @param <Q> 搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface BaseFindService<E extends IBaseEntity, Q extends SearchParams> extends SelectOne<E, Q>, SelectById<E>, SelectByIds<E>, SelectList<E, Q>, SelectPage<E, Q>, SelectCount<Q> {
}
