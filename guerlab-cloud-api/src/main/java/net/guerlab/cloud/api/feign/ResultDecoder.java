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

package net.guerlab.cloud.api.feign;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import net.guerlab.cloud.core.result.Result;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 结果解析.
 *
 * @author guer
 */
@Slf4j
public class ResultDecoder implements Decoder {

	private static final MediaType TEXT_MEDIA_TYPE = MediaType.parseMediaType("text/*");

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
		if (type instanceof Class && String.class.isAssignableFrom((Class<?>) type)) {
			if (!isJson(resultBody)) {
				return resultBody;
			}

			MediaType contentType = getContentType(response);
			if (contentType != null && TEXT_MEDIA_TYPE.includes(contentType)) {
				return resultBody;
			}
		}

		return parse0(resultBody, type);
	}

	private boolean isJson(String resultBody) {
		if (resultBody.startsWith("[") && resultBody.endsWith("]")) {
			return true;
		}
		return resultBody.startsWith("{") && resultBody.endsWith("}");
	}

	@Nullable
	private MediaType getContentType(Response response) {
		Collection<String> headerValues = response.headers().get(HttpHeaders.CONTENT_TYPE);
		if (headerValues == null) {
			return null;
		}

		for (String headerValue : headerValues) {
			if (headerValue != null) {
				try {
					return MediaType.parseMediaType(headerValue);
				}
				catch (Exception ignored) {
					// ignore this exception
				}
			}
		}
		return null;
	}

	@Nullable
	private Object parse0(String resultBody, Type type) throws IOException, FeignException {
		TypeReference<?> typeReference = new TypeReference<>() {

			@Override
			public Type getType() {
				return type;
			}
		};

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

			return objectMapper.readValue(resultBody, typeReference);
		}
		catch (JsonParseException e) {
			if (type.getTypeName().equals(String.class.getTypeName())) {
				return resultBody;
			}

			throw e;
		}
		catch (ApplicationException e) {
			throw e;
		}
		catch (Exception e) {
			log.debug(e.getLocalizedMessage(), e);
			throw new ApplicationException(e.getMessage(), e);
		}
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
