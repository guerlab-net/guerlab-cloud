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

package net.guerlab.cloud.auth.factory;

import jakarta.annotation.Nullable;

import org.springframework.core.Ordered;

import net.guerlab.cloud.auth.domain.TokenInfo;

/**
 * 抽象token工厂.
 *
 * @param <T> 数据实体类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface TokenFactory<T> extends Ordered, Comparable<TokenFactory<?>> {

	/**
	 * 链接符.
	 */
	String CONNECTORS = " ";

	/**
	 * 是否已启用.
	 *
	 * @return 已启用
	 */
	boolean enabled();

	/**
	 * 是否为默认token工厂.
	 *
	 * @return 否为默认token工厂
	 */
	boolean isDefault();

	/**
	 * 获取access token前缀.
	 *
	 * @return access token前缀
	 */
	String getAccessTokenPrefix();

	/**
	 * 获取refresh token前缀.
	 *
	 * @return refresh token前缀
	 */
	String getRefreshTokenPrefix();

	/**
	 * 判断是否使用该token工厂.
	 *
	 * @param token token
	 * @return 是否使用
	 */
	default boolean accept(String token) {
		return acceptAccessToken(token) || acceptRefreshToken(token);
	}

	/**
	 * 判断是否使用该token工厂.
	 *
	 * @param token token
	 * @return 是否使用
	 */
	default boolean acceptAccessToken(@Nullable String token) {
		return token != null && token.startsWith(getAccessTokenPrefix());
	}

	/**
	 * 判断是否使用该token工厂.
	 *
	 * @param token token
	 * @return 是否使用
	 */
	default boolean acceptRefreshToken(@Nullable String token) {
		return token != null && token.startsWith(getRefreshTokenPrefix());
	}

	/**
	 * 判断是否使用该token工厂.
	 *
	 * @param ip ip
	 * @return 是否使用
	 */
	boolean acceptIp(String ip);

	/**
	 * 获取可使用的对象类型.
	 *
	 * @return 对象类型
	 */
	Class<T> getAcceptClass();

	/**
	 * 构造access token.
	 *
	 * @param entity 数据实体
	 * @return token信息
	 */
	TokenInfo generateByAccessToken(T entity);

	/**
	 * 构造refresh token.
	 *
	 * @param entity 数据实体
	 * @return token信息
	 */
	TokenInfo generateByRefreshToken(T entity);

	/**
	 * 根据access token解析数据实体.
	 *
	 * @param token access token
	 * @return 数据实体
	 */
	T parseByAccessToken(String token);

	/**
	 * 根据refresh token解析数据实体.
	 *
	 * @param token refresh token
	 * @return 数据实体
	 */
	T parseByRefreshToken(String token);
}
