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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import feign.FeignException;
import feign.Response;
import feign.Util;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.http.MediaType;

import net.guerlab.cloud.core.result.ApplicationStackTrace;
import net.guerlab.cloud.core.result.RemoteException;
import net.guerlab.cloud.core.result.Result;
import net.guerlab.commons.exception.ApplicationException;

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
			return new Default().decode(response, type);
		}

		String resultBody = Util.toString(body.asReader(StandardCharsets.UTF_8));
		TypeReference<?> typeReference = new TypeReference<>() {

			@Override
			public Type getType() {
				return type;
			}
		};

		try {
			if (isResultType(type)) {
				Result<?> result = (Result<?>) objectMapper.readValue(resultBody, typeReference);
				if (!result.isStatus()) {
					String message = result.getMessage();
					List<ApplicationStackTrace> stackTraces = result.getStackTraces();
					int errorCode = result.getErrorCode();
					RemoteException remoteException = RemoteException.build(message, stackTraces);
					throw new ApplicationException(message, remoteException, errorCode);
				}
				return result;
			}

			boolean bodyIsWrapped = getBodyIsWrapped(response);
			if (!bodyIsWrapped) {
				return objectMapper.readValue(resultBody, typeReference);
			}

			JsonNode rootNode = objectMapper.readTree(resultBody);

			if (rootNode.has(Constants.FIELD_STATUS) && rootNode.has(Constants.FIELD_ERROR_CODE)) {
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

	private boolean isResultType(Type type) {
		if (type instanceof Class<?>) {
			return Result.class.isAssignableFrom((Class<?>) type);
		}
		if (type instanceof ParameterizedType parameterizedType) {
			return Result.class.isAssignableFrom((Class<?>) parameterizedType.getRawType());
		}

		return false;
	}

	private boolean getBodyIsWrapped(Response response) {
		Collection<String> values = response.headers()
				.get(net.guerlab.cloud.commons.Constants.HTTP_HEADER_RESPONSE_WRAPPED);
		if (values == null) {
			return false;
		}
		return values.stream().map(StringUtils::trimToNull).anyMatch(Objects::nonNull);
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
