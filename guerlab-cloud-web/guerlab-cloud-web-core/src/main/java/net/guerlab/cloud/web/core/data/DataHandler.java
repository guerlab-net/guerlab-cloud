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

package net.guerlab.cloud.web.core.data;

/**
 * 数据处理.
 *
 * @author guer
 */
public interface DataHandler {

	/**
	 * 转换数据.
	 *
	 * @param value 源值
	 * @return 转换后的值
	 */
	Object transformation(Object value);
}
