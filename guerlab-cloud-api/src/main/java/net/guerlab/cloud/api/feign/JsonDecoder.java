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

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import feign.FeignException;
import feign.Response;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.MediaType;

import net.guerlab.cloud.core.result.ApplicationStackTrace;
import net.guerlab.cloud.core.result.RemoteException;
import net.guerlab.cloud.core.result.Result;
import net.guerlab.commons.exception.ApplicationException;

import static net.guerlab.cloud.core.Constants.HTTP_HEADER_RESPONSE_WRAPPED;

/**
 * json结果解析.
 *
 * @author guer
 */
@Slf4j
public class JsonDecoder implements TypeDecoder {

	private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON;

	private final ObjectMapper objectMapper;

	/**
	 * 初始化结果解析.
	 *
	 * @param objectMapper objectMapper
	 */
	public JsonDecoder(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean isSupport(MediaType mediaType) {
		return MEDIA_TYPE.includes(mediaType);
	}

	@Nullable
	@Override
	public Object decode(Response response, Type type) throws IOException, FeignException {
		Response.Body body = response.body();
		if (body == null) {
			return null;
		}

		byte[] bodyBytes = body.asInputStream().readAllBytes();
		JavaType javaType = objectMapper.getTypeFactory().constructType(type);

		try {
			if (isResultType(type)) {
				return decodeResultType(bodyBytes, javaType);
			}
			else if (!getBodyIsWrapped(response)) {
				return objectMapper.readValue(bodyBytes, javaType);
			}
			return decodeWithWrapped(bodyBytes, javaType);
		}
		catch (JsonParseException e) {
			if (type.getTypeName().equals(String.class.getTypeName())) {
				return new String(bodyBytes);
			}

			throw e;
		}
		catch (Exception e) {
			log.debug(e.getLocalizedMessage(), e);
			if (e instanceof RuntimeException re) {
				throw re;
			}
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	private boolean isResultType(Type type) {
		if (type instanceof Class<?>) {
			return Result.class.isAssignableFrom((Class<?>) type);
		}
		if (type instanceof ParameterizedType parameterizedType) {
			return Result.class.isAssignableFrom((Class<?>) parameterizedType.getRawType());
		}

		return false;
	}

	private Object decodeResultType(byte[] bodyBytes, JavaType javaType) throws IOException {
		Result<?> result = objectMapper.readValue(bodyBytes, javaType);
		if (result.isStatus()) {
			return result;
		}

		String message = result.getMessage();
		List<ApplicationStackTrace> stackTraces = result.getStackTraces();
		int errorCode = result.getErrorCode();
		RemoteException remoteException = RemoteException.build(message, stackTraces);
		throw new ApplicationException(message, remoteException, errorCode);
	}

	private boolean getBodyIsWrapped(Response response) {
		Collection<String> values = response.headers().get(HTTP_HEADER_RESPONSE_WRAPPED);
		if (values == null) {
			return false;
		}
		return values.stream().map(StringUtils::trimToNull).anyMatch(Objects::nonNull);
	}

	@Nullable
	private Object decodeWithWrapped(byte[] bodyBytes, JavaType javaType) throws IOException {
		JsonNode rootNode = objectMapper.readTree(bodyBytes);

		if (rootNode.has(Constants.FIELD_STATUS) && rootNode.has(Constants.FIELD_ERROR_CODE)) {
			if (!getStatus(rootNode)) {
				throw FailParser.parse(rootNode);
			}
			else if (!rootNode.has(Constants.FIELD_DATA)) {
				log.debug("rootNode not has {} field", Constants.FIELD_DATA);
				return null;
			}

			return objectMapper.convertValue(rootNode.get(Constants.FIELD_DATA), javaType);
		}

		return objectMapper.readValue(bodyBytes, javaType);
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
