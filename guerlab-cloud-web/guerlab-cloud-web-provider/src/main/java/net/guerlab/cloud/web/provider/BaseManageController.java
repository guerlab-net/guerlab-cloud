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

package net.guerlab.cloud.web.provider;

import java.util.Collections;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.commons.api.DeleteById;
import net.guerlab.cloud.commons.api.ManageApi;
import net.guerlab.cloud.commons.api.UpdateById;
import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.log.annotation.Log;
import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 基础管理控制器实现.
 *
 * @param <E>  实体类型
 * @param <SP> 搜索参数类型
 * @param <A>  api接口类型
 * @param <V>  返回对象类型
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
@Getter
public abstract class BaseManageController<E extends IBaseEntity, SP extends SearchParams, A extends ManageApi<E, SP>, V> extends BaseQueryController<E, SP, A, V> {

	/**
	 * 根据api实例创建控制器.
	 *
	 * @param api api实例
	 */
	public BaseManageController(A api) {
		super(api);
	}

	@Log("method.insert")
	@PostMapping
	@Operation(summary = "新增实体", security = @SecurityRequirement(name = Constants.TOKEN))
	public V insert(@RequestBody E entity) {
		entity = getApi().insert(entity);
		V vo = convert(entity);
		afterFind(Collections.singletonList(vo), null);
		return vo;
	}

	@Log("method.updateById")
	@PostMapping(UpdateById.UPDATE_BY_ID_PATH)
	@Operation(summary = "根据Id编辑数据", security = @SecurityRequirement(name = Constants.TOKEN))
	public V updateById(@RequestBody E entity) {
		Long id = entity.id();
		if (id == null) {
			throw nullPointException();
		}
		getApi().updateById(entity);
		V vo = selectById(id, null);
		if (vo == null) {
			throw nullPointException();
		}
		return vo;
	}

	@Log("method.deleteById")
	@DeleteMapping(DeleteById.DELETE_BY_ID_PATH)
	@Operation(summary = "根据Id删除数据", security = @SecurityRequirement(name = Constants.TOKEN))
	public void deleteById(@Parameter(description = "ID", required = true) @PathVariable(DeleteById.DELETE_BY_ID_PARAM) Long id) {
		getApi().deleteById(id);
	}

	@Log("method.delete")
	@DeleteMapping
	@Operation(summary = "根据搜索参数删除数据", security = @SecurityRequirement(name = Constants.TOKEN))
	public void delete(@Parameter(description = "搜索参数", required = true) @RequestBody SP searchParams) {
		getApi().delete(searchParams);
	}
}
