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

package net.guerlab.cloud.server.mybatis.plus.methods;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import net.guerlab.cloud.commons.entity.BaseEntity;
import net.guerlab.commons.reflection.FieldUtil;

/**
 * 自动加载注入方法.
 *
 * @author guer
 */
public abstract class AbstractAutoLoadMethod extends AbstractMethod {

	/**
	 * BaseEntity字段列表.
	 */
	protected static final Collection<Field> BASE_ENTITY_FIELDS = FieldUtil.getFields(BaseEntity.class);

	/**
	 * 获取字段列表.
	 *
	 * @param tableInfo
	 *         表信息
	 * @return 字段列表
	 */
	protected static Stream<TableFieldInfo> getFieldStream(TableInfo tableInfo) {
		return tableInfo.getFieldList().stream().filter(tableField -> filedFilter(tableInfo, tableField));
	}

	/**
	 * 字段过滤.
	 *
	 * @param tableInfo
	 *         表信息
	 * @param fieldInfo
	 *         表字段信息
	 * @return 是否有效
	 */
	protected static boolean filedFilter(TableInfo tableInfo, TableFieldInfo fieldInfo) {
		if (BaseEntity.class.isAssignableFrom(tableInfo.getEntityType())) {
			return !BASE_ENTITY_FIELDS.contains(fieldInfo.getField());
		}
		return true;
	}
}
