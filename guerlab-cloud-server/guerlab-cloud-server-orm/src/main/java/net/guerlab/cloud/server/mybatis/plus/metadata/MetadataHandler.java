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

package net.guerlab.cloud.server.mybatis.plus.metadata;

import jakarta.annotation.Nullable;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 元数据处理.
 *
 * @author guer
 */
public interface MetadataHandler {

	/**
	 * 插入元对象字段填充.
	 *
	 * @param metaObject 元对象
	 */
	default void insertFill(MetaObject metaObject) {
	}

	/**
	 * 更新元对象字段填充.
	 *
	 * @param metaObject 元对象
	 */
	default void updateFill(MetaObject metaObject) {
	}

	/**
	 * 通用填充.
	 *
	 * @param fieldName  java bean property name
	 * @param fieldVal   java bean property value
	 * @param metaObject meta object parameter
	 */
	default void setFieldValByName(String fieldName, @Nullable Object fieldVal, MetaObject metaObject) {
		if (fieldVal != null && metaObject.hasSetter(fieldName)) {
			metaObject.setValue(fieldName, fieldVal);
		}
	}
}
