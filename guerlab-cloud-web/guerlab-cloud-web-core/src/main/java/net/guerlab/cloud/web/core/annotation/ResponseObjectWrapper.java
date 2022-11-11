/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对响应结果进行包装.
 *
 * @author guer
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ResponseObjectWrapper {

	/**
	 * 是否忽略对结果包装.
	 *
	 * @return 是否忽略对结果包装
	 */
	boolean ignore() default false;

	/**
	 * 是否忽略数据类型检查，并强制包装.
	 *
	 * @return 是否忽略数据类型检查，并强制包装
	 */
	boolean force() default false;
}
