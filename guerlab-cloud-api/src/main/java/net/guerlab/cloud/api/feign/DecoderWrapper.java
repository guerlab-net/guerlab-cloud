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
import java.util.Collection;
import java.util.List;

import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * 解析包装器.
 *
 * @author guer
 */
@Slf4j
public class DecoderWrapper implements Decoder {

	private final Decoder defaultDecoder;

	private final ObjectProvider<TypeDecoder> typeDecoders;

	public DecoderWrapper(Decoder defaultDecoder, ObjectProvider<TypeDecoder> typeDecoders) {
		this.defaultDecoder = defaultDecoder;
		this.typeDecoders = typeDecoders;
	}

	@Override
	public Object decode(Response response, Type type) throws IOException, FeignException {
		MediaType mediaType = getContentType(response);

		if (mediaType != null) {
			for (TypeDecoder typeDecoder : typeDecoders) {
				if (typeDecoder.isSupport(mediaType)) {
					return typeDecoder.decode(response, type);
				}
			}
		}

		return defaultDecoder.decode(response, type);
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
}
