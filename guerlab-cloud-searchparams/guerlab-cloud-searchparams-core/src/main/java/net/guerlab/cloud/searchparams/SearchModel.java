/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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
 * 搜索模式.
 *
 * @author guer
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SearchModel {

	/**
	 * 获取搜索模式类型.
	 *
	 * @return 搜索模式类型
	 */
	SearchModelType value() default SearchModelType.EQUAL_TO;

	/**
	 * 自定义sql.
	 *
	 * @return 自定义sql
	 */
	String sql() default "";

	/**
	 * 自定义sql提供器列表.
	 *
	 * @return 自定义sql提供器列表
	 */
	Class<? extends SqlProvider>[] sqlProviders() default {};
}
