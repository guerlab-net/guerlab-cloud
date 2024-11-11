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

package net.guerlab.cloud.searchparams;

/**
 * 搜索模式类型.
 *
 * @author guer
 */
public enum SearchModelType {

	/**
	 * 空.
	 */
	IS_NULL,

	/**
	 * 非空.
	 */
	IS_NOT_NULL,

	/**
	 * 相等.
	 */
	EQUAL_TO,

	/**
	 * 不想等.
	 */
	NOT_EQUAL_TO,

	/**
	 * 大于.
	 */
	GREATER_THAN,

	/**
	 * 大于等于.
	 */
	GREATER_THAN_OR_EQUAL_TO,

	/**
	 * 小于.
	 */
	LESS_THAN,

	/**
	 * 小于等于.
	 */
	LESS_THAN_OR_EQUAL_TO,

	/**
	 * 包含.
	 */
	LIKE,

	/**
	 * 不包含.
	 */
	NOT_LIKE,

	/**
	 * 前匹配.
	 */
	START_WITH,

	/**
	 * 前不匹配.
	 */
	START_NOT_WITH,

	/**
	 * 后匹配.
	 */
	END_WITH,

	/**
	 * 后不匹配.
	 */
	END_NOT_WITH,

	/**
	 * 在列表中,只能配合Collection接口使用.
	 */
	IN,

	/**
	 * 不在列表中,只能配合Collection接口使用.
	 */
	NOT_IN,

	/**
	 * 介于两者之间, 搭配{@link net.guerlab.cloud.searchparams.Between}一起使用.
	 */
	BETWEEN,

	/**
	 * 忽略.
	 */
	IGNORE,

	/**
	 * 自定义SQL.
	 */
	CUSTOM_SQL,
}
