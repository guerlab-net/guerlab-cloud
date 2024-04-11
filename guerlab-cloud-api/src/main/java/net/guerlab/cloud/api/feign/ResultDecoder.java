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

package net.guerlab.cloud.api.feign;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.core.result.Result;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 结果解析.
 *
 * @author guer
 */
@Slf4j
public class ResultDecoder implements Decoder {

	private final ObjectMapper objectMapper;

	/**
	 * 初始化结果解析.
	 *
	 * @param objectMapper objectMapper
	 */
	public ResultDecoder(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Nullable
	@Override
	public Object decode(Response response, Type type) throws IOException, FeignException {
		Response.Body body = response.body();

		if (body == null) {
			return new Decoder.Default().decode(response, type);
		}

		String resultBody = Util.toString(body.asReader(StandardCharsets.UTF_8));

		if (!isJson(resultBody)) {
			if (type instanceof Class && String.class.isAssignableFrom((Class<?>) type)) {
				return resultBody;
			}
		}

		TypeReference<?> typeReference = new TypeReference<>() {

			@Override
			public Type getType() {
				return type;
			}
		};

		try {
			try {
				JsonNode rootNode = objectMapper.readTree(resultBody);
				if (type instanceof Class && Result.class.isAssignableFrom((Class<?>) type)) {
					return objectMapper.readValue(resultBody, typeReference);
				}
				else if (rootNode.has(Constants.FIELD_STATUS) && rootNode.has(Constants.FIELD_ERROR_CODE)) {
					if (!getStatus(rootNode)) {
						throw FailParser.parse(rootNode);
					}
					else if (!rootNode.has(Constants.FIELD_DATA)) {
						log.debug("rootNode not has {} field, json is : {}", Constants.FIELD_DATA, resultBody);
						return null;
					}

					return objectMapper.convertValue(rootNode.get(Constants.FIELD_DATA), typeReference);
				}
				else {
					return objectMapper.readValue(resultBody, typeReference);
				}
			}
			catch (JsonParseException e) {
				if (type.getTypeName().equals(String.class.getTypeName())) {
					return resultBody;
				}

				throw e;
			}
		}
		catch (ApplicationException e) {
			throw e;
		}
		catch (Exception e) {
			log.debug(e.getLocalizedMessage(), e);
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	private boolean isJson(String resultBody) {
		if (resultBody.startsWith("[") && resultBody.endsWith("]")) {
			return true;
		}
		return resultBody.startsWith("{") && resultBody.endsWith("}");
	}

	private boolean getStatus(JsonNode rootNode) {
		if (!rootNode.has(Constants.FIELD_STATUS)) {
			return true;
		}

		JsonNode statusNode = rootNode.get(Constants.FIELD_STATUS);

		if (!statusNode.isBoolean()) {
			return true;
		}

		BooleanNode node = (BooleanNode) statusNode;

		return node.asBoolean();
	}
}
