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

import java.util.Collection;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.guerlab.cloud.commons.api.Api;
import net.guerlab.cloud.commons.entity.BaseEntity;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.log.annotation.Log;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.service.BaseService;

/**
 * 基础控制器实现.
 *
 * @param <E>  实体类型
 * @param <SP> 搜索参数类型
 * @param <S>  服务接口类型
 * @author guer
 */
@SuppressWarnings("unused")
@Slf4j
public abstract class BaseController<E, SP extends SearchParams, S extends BaseService<E, SP>> implements Api<E, SP> {

	/**
	 * 服务接口.
	 */
	protected final S service;

	/**
	 * 根据服务实例创建控制器.
	 *
	 * @param service 服务实例
	 */
	public BaseController(S service) {
		this.service = service;
	}

	/**
	 * 获取服务接口.
	 *
	 * @return 服务接口
	 */
	public S getService() {
		return service;
	}

	@Nullable
	@Override
	public E selectById(@PathVariable(SELECT_BY_ID_PARAM) Long id, @Nullable SP searchParams) {
		E entity = getService().selectById(id);
		if (entity == null) {
			if (queryAllowReturnNull()) {
				return null;
			}
			throw nullPointException();
		}
		afterFind(Collections.singletonList(entity), searchParams);
		return entity;
	}

	@Nullable
	@Override
	public E selectOne(@RequestBody SP searchParams) {
		beforeFind(searchParams);
		E entity = getService().selectOne(searchParams);
		if (entity == null) {
			if (queryAllowReturnNull()) {
				return null;
			}
			throw nullPointException();
		}
		afterFind(Collections.singletonList(entity), searchParams);
		return entity;
	}

	/**
	 * 查询是否允许返回null.
	 *
	 * @return 查询是否允许返回null
	 */
	protected abstract boolean queryAllowReturnNull();

	/**
	 * 当对象为空的时候抛出的异常.
	 *
	 * @return 当对象为空的时候抛出的异常
	 */
	@SuppressWarnings("SameReturnValue")
	protected RuntimeException nullPointException() {
		return new NullPointerException();
	}

	@Override
	public Collection<E> selectList(@RequestBody SP searchParams) {
		beforeFind(searchParams);
		Collection<E> list = getService().selectList(searchParams);
		afterFind(list, searchParams);
		return list;
	}

	@Override
	public Pageable<E> selectPage(@RequestBody SP searchParams,
			@RequestParam(name = PAGE_ID, defaultValue = PAGE_ID_VALUE, required = false) int pageId,
			@RequestParam(name = PAGE_SIZE, defaultValue = PAGE_SIZE_VALUE, required = false) int pageSize) {
		beforeFind(searchParams);
		Pageable<E> result = getService().selectPage(searchParams, pageId, pageSize);
		afterFind(result.getList(), searchParams);
		return result;
	}

	@Override
	public long selectCount(@RequestBody SP searchParams) {
		beforeFind(searchParams);
		return getService().selectCount(searchParams);
	}

	/**
	 * 查询前置环绕.
	 *
	 * @param searchParams 搜索参数
	 */
	@SuppressWarnings("EmptyMethod")
	protected void beforeFind(SP searchParams) {

	}

	/**
	 * 查询后置焕然.
	 *
	 * @param list         结果列表
	 * @param searchParams 搜索参数
	 */
	@SuppressWarnings("EmptyMethod")
	protected void afterFind(Collection<E> list, @Nullable SP searchParams) {

	}

	@Log("method.insert")
	@Override
	public E insert(E entity) {
		service.insert(entity);
		return entity;
	}

	@Log("method.updateById")
	@Override
	public E updateById(Long id, E entity) {
		E inStorageEntity = service.selectById(id);
		if (inStorageEntity == null) {
			throw nullPointException();
		}
		copyProperties(entity, inStorageEntity, id);
		getService().updateById(inStorageEntity);
		return inStorageEntity;
	}

	/**
	 * 拷贝属性.
	 *
	 * @param requestEntity   请求对象
	 * @param inStorageEntity 在库对象
	 * @param id              主键
	 */
	protected void copyProperties(E requestEntity, E inStorageEntity, @Nullable Long id) {
		if (inStorageEntity instanceof BaseEntity tempEntity) {
			Long version = tempEntity.getVersion();
			BeanUtils.copyProperties(requestEntity, inStorageEntity);
			tempEntity.setVersion(version);

			if (id != null) {
				tempEntity.setId(id);
			}
		}
		else {
			BeanUtils.copyProperties(requestEntity, inStorageEntity);
		}
	}

	@Log("method.deleteById")
	@Override
	public void deleteById(Long id) {
		service.deleteById(id);
	}

	@Log("method.delete")
	@Override
	public void delete(SP searchParams) {
		service.delete(searchParams);
	}
}
