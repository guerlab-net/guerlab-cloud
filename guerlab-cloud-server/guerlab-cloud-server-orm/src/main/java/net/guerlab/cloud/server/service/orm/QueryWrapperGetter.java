/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

import com.baomidou.mybatisplus.core.conditions.interfaces.Join;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.searchparams.SearchParamsUtils;

/**
 * QueryWrapper获取接口.
 *
 * @param <E> 实体类型
 * @param <Q> 搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface QueryWrapperGetter<E extends IBaseEntity, Q extends SearchParams> {

	/**
	 * 获取QueryWrapper，并通过searchParams对象对QueryWrapper进行赋值.
	 *
	 * @param searchParams 搜索对象
	 * @return QueryWrapper
	 */
	default QueryWrapper<E> getQueryWrapperWithSelectMethod(Q searchParams) {
		return getQueryWrapper(searchParams);
	}

	/**
	 * 获取QueryWrapper.
	 *
	 * @return QueryWrapper
	 */
	default QueryWrapper<E> getQueryWrapperWithSelectMethod() {
		return getQueryWrapper();
	}

	/**
	 * 获取QueryWrapper，并通过searchParams对象对QueryWrapper进行赋值.
	 *
	 * @param searchParams 搜索对象
	 * @return QueryWrapper
	 */
	default QueryWrapper<E> getQueryWrapper(Q searchParams) {
		QueryWrapper<E> wrapper = getQueryWrapper();

		SearchParamsUtils.handler(searchParams, wrapper);

		return wrapper;
	}

	/**
	 * 获取QueryWrapper.
	 *
	 * @return QueryWrapper
	 */
	default QueryWrapper<E> getQueryWrapper() {
		QueryWrapper<E> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntityClass(getEntityClass());
		return queryWrapper;
	}

	/**
	 * 获取LambdaQueryWrapper.
	 *
	 * @return LambdaQueryWrapper
	 */
	default LambdaQueryWrapper<E> getLambdaQueryWrapper() {
		LambdaQueryWrapper<E> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.setEntityClass(getEntityClass());
		return queryWrapper;
	}

	/**
	 * 获取实体类型.
	 *
	 * @return 实体类型
	 */
	Class<E> getEntityClass();

	/**
	 * 查询加锁.
	 *
	 * @param wrapper QueryWrapper
	 */
	default void withLock(Join<E> wrapper) {
		wrapper.last("FOR UPDATE");
	}
}
