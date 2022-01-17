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
import net.guerlab.cloud.server.service.BaseService;

/**
 * 基础控制器
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
@SuppressWarnings("unused")
public abstract class BaseController<D, E, S extends BaseService<E, SP>, SP extends SearchParams>
        extends BaseFindController<D, E, S, SP>
        implements SaveController<D, E, S, SP>, UpdateController<D, E, S, SP>, DeleteController<D, E, S, SP> {

}
