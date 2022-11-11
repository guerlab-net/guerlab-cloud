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

package net.guerlab.cloud.server.service;

import java.util.Collection;
import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 基本查询服务接口.
 *
 * @param <T>  数据类型
 * @param <SP> 搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface BaseFindService<T, SP extends SearchParams> extends QueryWrapperGetter<T, SP> {

	/**
	 * 查询单一结果，根据实体内非null字段按照值相等方式查询.
	 *
	 * @param entity 实体
	 * @return 实体
	 */
	@Nullable
	T selectOne(T entity);

	/**
	 * 查询单一结果，根据实体内非null字段按照值相等方式查询.
	 *
	 * @param entity 实体
	 * @return Optional
	 */
	default Optional<T> selectOneOptional(T entity) {
		return Optional.ofNullable(selectOne(entity));
	}

	/**
	 * 查询单一结果，根据搜索参数进行筛选.
	 *
	 * @param searchParams 搜索参数对象
	 * @return 实体
	 */
	@Nullable
	T selectOne(SP searchParams);

	/**
	 * 查询单一结果，根据搜索参数进行筛选.
	 *
	 * @param searchParams 搜索参数对象
	 * @return Optional
	 */
	default Optional<T> selectOneOptional(SP searchParams) {
		return Optional.ofNullable(selectOne(searchParams));
	}

	/**
	 * 通过Id查询单一结果.
	 *
	 * @param id 主键id
	 * @return 实体
	 */
	@Nullable
	T selectById(Long id);

	/**
	 * 通过Id查询单一结果.
	 *
	 * @param id 主键id
	 * @return Optional
	 */
	default Optional<T> selectByIdOptional(Long id) {
		return Optional.ofNullable(selectById(id));
	}

	/**
	 * 查询列表.
	 *
	 * @param entity 实体
	 * @return 实体列表
	 */
	Collection<T> selectList(T entity);

	/**
	 * 查询列表.
	 *
	 * @param queryWrapper 查询条件
	 * @return 实体列表
	 */
	Collection<T> selectList(QueryWrapper<T> queryWrapper);

	/**
	 * 查询列表.
	 *
	 * @param queryWrapper 查询条件
	 * @return 实体列表
	 */
	Collection<T> selectList(LambdaQueryWrapper<T> queryWrapper);

	/**
	 * 获取所有对象.
	 *
	 * @return 实体列表
	 */
	Collection<T> selectList();

	/**
	 * 获取所有对象.
	 *
	 * @param searchParams 搜索参数对象
	 * @return 实体列表
	 */
	Collection<T> selectList(SP searchParams);

	/**
	 * 查询列表.
	 *
	 * @param searchParams 搜索参数对象
	 * @param pageId       分页ID
	 * @param pageSize     分页尺寸
	 * @return 实体列表
	 */
	Pageable<T> selectPage(SP searchParams, int pageId, int pageSize);

	/**
	 * 查询总记录数.
	 *
	 * @param entity 实体
	 * @return 实体总数
	 */
	long selectCount(T entity);

	/**
	 * 查询总记录数.
	 *
	 * @param searchParams 搜索参数对象
	 * @return 实体总数
	 */
	long selectCount(SP searchParams);

}
