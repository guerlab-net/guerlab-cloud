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

package net.guerlab.cloud.searchparams;

/**
 * 自定义sql提供器.
 *
 * @author guer
 */
public interface SqlProvider {

	/**
	 * 判断是否允许处理.
	 *
	 * @param fieldClass 字段类型
	 * @param object     处理对象
	 * @return 是否处理
	 */
	boolean accept(Class<?> fieldClass, Object object);

	/**
	 * 处理.
	 *
	 * @param object 处理对象
	 * @param value  参数值
	 */
	void apply(Object object, Object value);
}
