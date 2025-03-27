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

package net.guerlab.cloud.server.mybatis.plus.methods;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.commons.reflection.FieldUtil;

/**
 * 自动加载注入方法.
 *
 * @param <E> 基础类类型
 * @author guer
 */
public abstract class AbstractAutoLoadMethod<E extends IBaseEntity> extends AbstractMethod {

	/**
	 * 基础类类型.
	 */
	protected final Class<E> baseClass;

	/**
	 * 基础字段列表.
	 */
	protected final transient Collection<Field> baseFields;

	/**
	 * 构造自动加载注入方法.
	 *
	 * @param baseClass  基础类
	 * @param methodName 方法名
	 */
	protected AbstractAutoLoadMethod(Class<E> baseClass, String methodName) {
		super(methodName);
		this.baseClass = baseClass;
		baseFields = FieldUtil.getFields(baseClass);
	}

	/**
	 * 获取字段列表.
	 *
	 * @param tableInfo 表信息
	 * @return 字段列表
	 */
	protected Stream<TableFieldInfo> getFieldStream(TableInfo tableInfo) {
		return tableInfo.getFieldList().stream().filter(tableField -> filedFilter(tableInfo, tableField));
	}

	/**
	 * 字段过滤.
	 *
	 * @param tableInfo 表信息
	 * @param fieldInfo 表字段信息
	 * @return 是否有效
	 */
	protected boolean filedFilter(TableInfo tableInfo, TableFieldInfo fieldInfo) {
		if (baseClass.isAssignableFrom(tableInfo.getEntityType())) {
			return !baseFields.contains(fieldInfo.getField());
		}
		return true;
	}
}
