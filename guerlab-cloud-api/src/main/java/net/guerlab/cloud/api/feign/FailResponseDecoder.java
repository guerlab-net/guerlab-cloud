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

package net.guerlab.cloud.api.feign;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

/**
 * 失败响应解析.
 *
 * @author guer
 */
@Slf4j
public class FailResponseDecoder implements OrderedErrorDecoder {

	private final ObjectMapper objectMapper;

	/**
	 * 初始化失败响应解析.
	 *
	 * @param objectMapper objectMapper
	 */
	public FailResponseDecoder(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	@Nullable
	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.body() == null) {
			return null;
		}

		try {
			String resultBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
			JsonNode rootNode = objectMapper.readTree(resultBody);

			if (rootNode.has(Constants.FIELD_ERROR_CODE)) {
				return FailParser.parse(rootNode);
			}

			return null;
		}
		catch (Exception e) {
			log.debug(e.getLocalizedMessage(), e);
			return null;
		}
	}
}
