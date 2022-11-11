/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

package net.guerlab.cloud.server.web;

import lombok.extern.slf4j.Slf4j;

import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.service.BaseService;

/**
 * 基础Api实现.
 *
 * @param <E>  实体类型
 * @param <SP> 搜索参数类型
 * @param <S>  服务接口类型
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class BaseApi<E, SP extends SearchParams, S extends BaseService<E, SP>>
		extends BaseController<E, SP, S> {

	/**
	 * 根据服务实例创建控制器.
	 *
	 * @param service 服务实例
	 */
	public BaseApi(S service) {
		super(service);
	}

	@Override
	protected boolean queryAllowReturnNull() {
		return true;
	}
}
