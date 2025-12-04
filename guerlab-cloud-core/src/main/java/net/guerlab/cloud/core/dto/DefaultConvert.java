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

package net.guerlab.cloud.core.dto;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;

import org.springframework.beans.BeanUtils;

import net.guerlab.commons.exception.ApplicationException;

/**
 * 转换为对象.
 *
 * @param <D> 对象类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface DefaultConvert<D> extends Convert<D> {

	/**
	 * 从type列表中获取对象类型.
	 *
	 * @param types type列表
	 * @return 对象类型
	 */
	@Nullable
	private static Type getType(Type[] types) {
		for (Type type : types) {
			if (type instanceof ParameterizedType parameterizedType) {

				Type rawType = parameterizedType.getRawType();

				if (DefaultConvert.class.equals(rawType)) {
					return parameterizedType.getActualTypeArguments()[0];
				}
			}
		}
		return null;
	}

	/**
	 * 转换.
	 *
	 * @return 转换对象
	 */
	@Override
	@JsonIgnore
	@SuppressWarnings("unchecked")
	default D convert() {
		Class<D> clazz = (Class<D>) getType(getClass().getGenericInterfaces());

		if (clazz == null) {
			throw new ApplicationException("get convert object class fail");
		}

		D target;

		try {
			target = clazz.getDeclaredConstructor().newInstance();
		}
		catch (Exception e) {
			throw new ApplicationException(e.getLocalizedMessage(), e);
		}

		BeanUtils.copyProperties(this, target);

		return target;
	}
}
