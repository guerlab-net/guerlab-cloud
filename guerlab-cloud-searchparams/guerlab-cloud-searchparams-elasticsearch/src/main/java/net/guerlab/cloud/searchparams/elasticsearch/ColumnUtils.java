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

package net.guerlab.cloud.searchparams.elasticsearch;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import org.springframework.core.ResolvableType;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 字段工具类.
 *
 * @author guer
 */
public final class ColumnUtils {

	private ColumnUtils() {
	}

	/**
	 * 获取字段名称.
	 *
	 * @param field 字段
	 * @return 字段名
	 */
	public static String getFieldName(Field field) {
		org.springframework.data.elasticsearch.annotations.Field fieldAnnotations = field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);

		String result = field.getName();
		if (fieldAnnotations != null) {
			String annotationName = StringUtils.trimToNull(fieldAnnotations.name());
			if (annotationName != null) {
				result = annotationName;
			}
		}

		return result;
	}

	/**
	 * 获取查询字段名称.
	 *
	 * @param field      字段
	 * @param columnName 字段名
	 * @return 查询字段名称
	 */
	public static String getColumnName(Field field, String columnName) {
		org.springframework.data.elasticsearch.annotations.Field fieldAnnotations = field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
		if (fieldAnnotations == null) {
			return columnName;
		}

		Type type = getType(field);
		boolean isStringType = String.class.equals(type);
		if (!isStringType) {
			return columnName;
		}

		String annotationName = StringUtils.trimToNull(fieldAnnotations.name());

		String result = columnName;
		if (annotationName != null) {
			result = annotationName;
		}

		return result;
	}

	/**
	 * 获取查询字段名称.
	 *
	 * @param field      字段
	 * @param columnName 字段名
	 * @return 查询字段名称
	 */
	public static String getQueryColumnName(Field field, String columnName) {
		Type type = getType(field);
		boolean isStringType = String.class.equals(type);
		org.springframework.data.elasticsearch.annotations.Field fieldAnnotations = field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
		FieldType fieldType = FieldType.Auto;

		String result = columnName;
		if (fieldAnnotations != null) {
			fieldType = fieldAnnotations.type();
			String annotationName = StringUtils.trimToNull(fieldAnnotations.name());
			if (annotationName != null) {
				result = annotationName;
			}
		}

		// 在Elasticsearch 5.x及以上版本中，若字段定义为text类型，系统会自动生成同名的keyword子字段（如name.keyword）。
		if (isStringType) {
			if (fieldType == FieldType.Auto || fieldType == FieldType.Text) {
				result = result + ".keyword";
			}
		}
		return result;
	}

	/**
	 * 获取类型.
	 *
	 * @param field 字段
	 * @return 类型
	 */
	public static Type getType(Field field) {
		ResolvableType resolvableType = ResolvableType.forField(field);
		if (resolvableType.isArray()) {
			return Objects.requireNonNull(resolvableType.getComponentType().resolve());
		}

		ResolvableType[] generics = resolvableType.getGenerics();
		if (generics.length == 0) {
			return resolvableType.getType();
		}

		Class<?> fieldClass = field.getType();
		if (Iterable.class.isAssignableFrom(fieldClass)) {
			return generics[0].getType();
		}

		return resolvableType.getType();
	}
}
