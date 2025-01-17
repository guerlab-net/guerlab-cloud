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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 子查询支持.
 *
 * @author guer
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SubQuerySupport {

	/**
	 * 结果字段名称.
	 *
	 * @return 结果字段名称
	 */
	String resultFieldName();

	/**
	 * 关联类型.
	 *
	 * @return 关联类型
	 */
	String refType() default "IN";

	/**
	 * 实体类型.
	 *
	 * @return 实体类型
	 */
	Class<?> entityClass();

	/**
	 * 表名称.
	 *
	 * @return 表名称
	 */
	String tableName() default "";

	/**
	 * 默认where条件
	 *
	 * @return 默认where条件
	 */
	String baseWhere() default "";
}
