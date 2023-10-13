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

package net.guerlab.cloud.auth.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.commons.exception.AccessTokenExpiredException;
import net.guerlab.cloud.commons.exception.AccessTokenInvalidException;
import net.guerlab.cloud.commons.exception.RefreshTokenExpiredException;
import net.guerlab.cloud.commons.exception.RefreshTokenInvalidException;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 令牌类型.
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Schema(name = "TokenType", description = "令牌类型")
public enum TokenType {

	/**
	 * AccessToken.
	 */
	@Schema(description = "Access Token")
	ACCESS_TOKEN {
		@Override
		public String simpleName() {
			return SIMPLE_NAME_ACCESS_TOKEN;
		}

		@Override
		public ApplicationException expiredException() {
			return new AccessTokenExpiredException();
		}

		@Override
		public ApplicationException invalidException() {
			return new AccessTokenInvalidException();
		}
	},

	/**
	 * RefreshToken.
	 */
	@Schema(description = "Refresh Token")
	REFRESH_TOKEN {
		@Override
		public String simpleName() {
			return SIMPLE_NAME_REFRESH_TOKEN;
		}

		@Override
		public ApplicationException expiredException() {
			return new RefreshTokenExpiredException();
		}

		@Override
		public ApplicationException invalidException() {
			return new RefreshTokenInvalidException();
		}
	};

	/**
	 * AccessToken简称.
	 */
	public static final String SIMPLE_NAME_ACCESS_TOKEN = "AT";

	/**
	 * RefreshToken简称.
	 */
	public static final String SIMPLE_NAME_REFRESH_TOKEN = "RT";

	/**
	 * 通过简短名称获取令牌类型.
	 *
	 * @param simpleName 简短名称
	 * @return 令牌类型
	 */
	@Nullable
	public static TokenType parseBySimpleName(String simpleName) {
		String name = StringUtils.trimToNull(simpleName);
		if (SIMPLE_NAME_ACCESS_TOKEN.equals(name)) {
			return ACCESS_TOKEN;
		}
		else if (SIMPLE_NAME_REFRESH_TOKEN.equals(name)) {
			return REFRESH_TOKEN;
		}
		else {
			return null;
		}
	}

	/**
	 * 获取简短名称.
	 *
	 * @return 简短名称
	 */
	public abstract String simpleName();

	/**
	 * 获取Token过期异常.
	 *
	 * @return Token过期异常
	 */
	public abstract ApplicationException expiredException();

	/**
	 * 获取Token无效异常.
	 *
	 * @return Token无效异常
	 */
	public abstract ApplicationException invalidException();
}
