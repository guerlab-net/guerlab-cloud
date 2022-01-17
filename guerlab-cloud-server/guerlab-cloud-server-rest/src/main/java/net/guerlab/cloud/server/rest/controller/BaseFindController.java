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
package net.guerlab.cloud.server.rest.controller;

import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.service.BaseFindService;

/**
 * 基础查询控制器
 *
 * @param <D>
 *         输出对象类型
 * @param <E>
 *         实体对象类型
 * @param <S>
 *         服务接口类型
 * @param <SP>
 *         搜索参数对象类型
 * @author guer
 */
public abstract class BaseFindController<D, E, S extends BaseFindService<E, SP>, SP extends SearchParams>
        extends AbstractControllerImpl<D, E, S, SP> implements FindController<D, E, S, SP> {

}
