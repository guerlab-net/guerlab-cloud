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

package net.guerlab.cloud.searchparams.map;

import java.lang.reflect.Field;
import java.util.Map;

import jakarta.annotation.Nullable;

import net.guerlab.cloud.searchparams.AbstractSearchParamsUtilInstance;
import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.SearchParamsHandler;

/**
 * Map处理实例.
 *
 * @author guer
 */
public class MapSearchParamsUtilInstance extends AbstractSearchParamsUtilInstance {

	private static final Class<?> CLAZZ = Map.class;

	/**
	 * 初始化Map处理实例.
	 */
	public MapSearchParamsUtilInstance() {
		setDefaultHandler(new DefaultHandler());
	}

	@Override
	public boolean accept(Object object) {
		return CLAZZ.isInstance(object);
	}

	private static class DefaultHandler implements SearchParamsHandler {

		@SuppressWarnings({"unchecked", "rawtypes"})
		@Override
		public void setValue(Object object, Field field, String columnName, Object value,
				SearchModelType searchModelType, @Nullable String customSql, @Nullable JsonField jsonField) {
			((Map) object).put(field.getName(), value);
		}

		@Override
		public int getOrder() {
			return LOWEST_PRECEDENCE;
		}
	}
}
