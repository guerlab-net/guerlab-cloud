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

package net.guerlab.cloud.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import net.guerlab.cloud.searchparams.SearchParams;

/**
 * 基本删除服务接口.
 *
 * @param <T>  数据类型
 * @param <SP> 搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface BaseDeleteService<T, SP extends SearchParams> extends QueryWrapperGetter<T, SP> {

	/**
	 * 删除.
	 *
	 * @param searchParams 搜索参数
	 */
	void delete(SP searchParams);

	/**
	 * 根据Id删除.
	 *
	 * @param id 主键值
	 */
	void deleteById(Long id);

	/**
	 * 删除，调用此方法会忽略删除检查逻辑.
	 *
	 * @param queryWrapper 删除条件
	 */
	void delete(LambdaQueryWrapper<T> queryWrapper);

	/**
	 * 删除，调用此方法会忽略删除检查逻辑.
	 *
	 * @param queryWrapper 删除条件
	 */
	void delete(QueryWrapper<T> queryWrapper);

}
