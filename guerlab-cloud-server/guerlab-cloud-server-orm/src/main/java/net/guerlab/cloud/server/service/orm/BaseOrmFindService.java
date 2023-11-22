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

package net.guerlab.cloud.server.service.orm;

import java.util.Optional;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.BaseFindService;

/**
 * 基本ORM查询服务接口.
 *
 * @param <E>  数据类型
 * @param <SP> 搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface BaseOrmFindService<E extends IBaseEntity, SP extends SearchParams> extends BaseFindService<E, SP>, QueryWrapperGetter<E, SP> {

	/**
	 * 查询单一结果，根据实体内非null字段按照值相等方式查询.
	 *
	 * @param entity 实体
	 * @return 实体
	 */
	@Nullable
	E selectOne(E entity);

	/**
	 * 查询单一结果，根据实体内非null字段按照值相等方式查询.
	 *
	 * @param entity 实体
	 * @return Optional
	 */
	default Optional<E> selectOneOptional(E entity) {
		return Optional.ofNullable(selectOne(entity));
	}

}
