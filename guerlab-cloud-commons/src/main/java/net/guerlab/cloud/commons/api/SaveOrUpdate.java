/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

package net.guerlab.cloud.commons.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.commons.valid.InsertOrUpdateValid;
import net.guerlab.cloud.commons.valid.InsertValid;
import net.guerlab.cloud.commons.valid.UpdateValid;

/**
 * APi定义.
 *
 * @param <E> 返回实体类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface SaveOrUpdate<E extends IBaseEntity> {

	/**
	 * 请求路径.
	 */
	String UPDATE_BY_ID_PATH = "/saveOrUpdate";

	/**
	 * 新增或保存.
	 *
	 * @param entity 实体
	 * @return 实体
	 */
	@Nullable
	@PostMapping(UPDATE_BY_ID_PATH)
	@Operation(summary = "新增或保存")
	E saveOrUpdate(@Validated({InsertValid.class, UpdateValid.class, InsertOrUpdateValid.class}) @RequestBody E entity);
}
