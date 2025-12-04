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

package net.guerlab.cloud.web.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.guerlab.cloud.web.core.data.DataHandler;
import net.guerlab.cloud.web.core.data.NopeDataHandler;

/**
 * 数据处理.
 *
 * @author guer
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataAccess {

	/**
	 * 字段名称.
	 *
	 * @return 字段名称
	 */
	String fieldName();

	/**
	 * 数据处理类.
	 *
	 * @return 数据处理类
	 */
	Class<? extends DataHandler> handlerClass() default NopeDataHandler.class;

	/**
	 * 数据处理类名称.
	 *
	 * @return 数据处理类名称
	 */
	String handlerName() default "";
}
