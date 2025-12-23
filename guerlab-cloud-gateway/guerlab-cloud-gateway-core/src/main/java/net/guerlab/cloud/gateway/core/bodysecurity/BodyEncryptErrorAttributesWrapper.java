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

package net.guerlab.cloud.gateway.core.bodysecurity;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.reactive.function.server.ServerRequest;

import net.guerlab.cloud.gateway.core.exception.ErrorAttributesWrapper;
import net.guerlab.cloud.rsa.RsaKeys;
import net.guerlab.cloud.rsa.RsaUtils;

/**
 * 内容加密错误信息包装处理器.
 *
 * @author guer
 */
public class BodyEncryptErrorAttributesWrapper implements ErrorAttributesWrapper {

	private final ObjectMapper objectMapper;

	/**
	 * 请求体安全配置.
	 */
	private final BodySecurityProperties properties;

	/**
	 * 创建实例.
	 *
	 * @param objectMapper ObjectMapper
	 * @param properties   请求体安全配置
	 */
	public BodyEncryptErrorAttributesWrapper(ObjectMapper objectMapper, BodySecurityProperties properties) {
		this.objectMapper = objectMapper;
		this.properties = properties;
	}

	@Override
	public Map<String, Object> wrapper(ServerRequest request, Map<String, Object> errorAttributes) {
		String requestMethod = request.method().name();
		String requestPath = request.path();

		RsaKeys rsaKeys = properties.getRsaKeys(requestMethod, requestPath, false);
		if (rsaKeys == null) {
			return errorAttributes;
		}

		try {
			byte[] encryptData = RsaUtils.encryptByPrivateKey(objectMapper.writeValueAsBytes(errorAttributes),
					rsaKeys.getPrivateKeyRef());

			return Collections.singletonMap("encryptBody", Base64.getEncoder().encodeToString(encryptData));
		}
		catch (Exception e) {
			return errorAttributes;
		}
	}
}
