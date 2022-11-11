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

package net.guerlab.cloud.auth.domain;

/**
 * 测试令牌信息接口.
 *
 * @author guer
 */
public interface ITestTokenInfo {

	/**
	 * 获取用户ID.
	 *
	 * @return 用户ID
	 */
	Long getUserId();

	/**
	 * 获取用户名.
	 *
	 * @return 用户名
	 */
	String getUsername();
}
