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

import java.util.Base64;
import java.util.Objects;

import jakarta.annotation.Nullable;
import org.apache.commons.codec.digest.DigestUtils;

import net.guerlab.cloud.auth.properties.Md5TokenFactoryProperties;

/**
 * 抽象md5 token工厂.
 *
 * @param <T> 数据实体类型
 * @param <P> 配置类型
 * @author guer
 */
public abstract class AbstractMd5TokenFactory<T, P extends Md5TokenFactoryProperties>
		extends AbstractStringValueTokenFactory<T, P> {

	private static final String TOKEN_CONNECTORS = ".";

	/**
	 * 创建token工厂.
	 *
	 * @param properties 配置
	 */
	protected AbstractMd5TokenFactory(P properties) {
		super(properties);
	}

	@Override
	protected String buildToken(String dataString, String key, long expire) {
		String sign = DigestUtils.md5Hex(dataString + TOKEN_CONNECTORS + key);
		return Base64.getEncoder().encodeToString(dataString.getBytes()) + TOKEN_CONNECTORS + sign;
	}

	@Nullable
	@Override
	protected String parseDataString(String token, String key) {
		int index = token.lastIndexOf(TOKEN_CONNECTORS);

		if (index < 0) {
			return null;
		}

		String dataString = new String(Base64.getDecoder().decode(token.substring(0, index)));
		String tokenSign = token.substring(index + 1);

		String sign = DigestUtils.md5Hex(dataString + TOKEN_CONNECTORS + key);

		if (!Objects.equals(sign, tokenSign)) {
			return null;
		}

		return dataString;
	}
}
