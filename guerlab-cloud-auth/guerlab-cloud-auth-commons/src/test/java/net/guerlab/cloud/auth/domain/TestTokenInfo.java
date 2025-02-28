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

package net.guerlab.cloud.auth.domain;

import lombok.Data;

/**
 * 测试令牌信息.
 *
 * @author guer
 */
@Data
public class TestTokenInfo implements ITestTokenInfo {

	/**
	 * 用户ID.
	 */
	private Long userId;

	/**
	 * 用户名.
	 */
	private String username;

	public TestTokenInfo() {
	}

	public TestTokenInfo(Long userId, String username) {
		this.userId = userId;
		this.username = username;
	}
}
