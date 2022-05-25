/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

package net.guerlab.cloud.web.core.utils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import net.guerlab.cloud.web.core.data.DataHandler;

/**
 * 数据处理工具类.
 *
 * @author guer
 */
public final class DataAccessUtils {

	private DataAccessUtils() {
	}

	/**
	 * 对象处理.
	 *
	 * @param filedName   字段名称
	 * @param object      对象
	 * @param dataHandler 数据处理器
	 */
	@SuppressWarnings("unchecked")
	public static void objectHandler(String filedName, @Nullable Object object, DataHandler dataHandler) {
		if (object == null) {
			return;
		}

		if (object instanceof Iterator iterator) {
			while (iterator.hasNext()) {
				Object obj = iterator.next();
				objectHandler(filedName, obj, dataHandler);
			}
		}
		else if (object instanceof Iterable iterable) {
			for (Object obj : iterable) {
				objectHandler(filedName, obj, dataHandler);
			}
		}
		else if (object.getClass().isArray()) {
			Object[] objects = (Object[]) object;
			for (Object obj : objects) {
				objectHandler(filedName, obj, dataHandler);
			}
		}
		else if (object instanceof Map map) {
			Object val = map.get(filedName);
			map.put(filedName, dataHandler.transformation(val));
		}
		else {
			Field field = ReflectionUtils.findField(object.getClass(), filedName);
			if (field == null) {
				return;
			}
			boolean access = field.canAccess(object);
			field.trySetAccessible();
			Object val = ReflectionUtils.getField(field, object);
			if (val == null) {
				return;
			}
			val = dataHandler.transformation(val);
			ReflectionUtils.setField(field, object, val);
			field.setAccessible(access);
		}
	}
}
