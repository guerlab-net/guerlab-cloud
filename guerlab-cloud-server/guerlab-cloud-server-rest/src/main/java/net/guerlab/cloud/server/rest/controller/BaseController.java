/*
 * Copyright 2018-2021 guerlab.net and other contributors.
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

import net.guerlab.cloud.core.dto.Convert;
import net.guerlab.cloud.searchparams.AbstractSearchParams;
import net.guerlab.cloud.server.service.BaseService;

import java.io.Serializable;

/**
 * 基础控制器
 *
 * @param <D>
 *         DTO对象类型
 * @param <E>
 *         实体对象类型
 * @param <S>
 *         服务接口类型
 * @param <SP>
 *         搜索参数对象类型
 * @param <PK>
 *         实体主键类型
 * @author guer
 */
@SuppressWarnings("unused")
public abstract class BaseController<D, E extends Convert<D>, S extends BaseService<E, PK, SP>, SP extends AbstractSearchParams, PK extends Serializable>
        extends BaseFindController<D, E, S, SP, PK>
        implements SaveController<D, E, S, SP, PK>, UpdateController<D, E, S, SP, PK>, DeleteController<D, E, S, SP, PK> {

}
