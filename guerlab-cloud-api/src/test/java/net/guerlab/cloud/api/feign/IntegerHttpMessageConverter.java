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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

public class IntegerHttpMessageConverter extends AbstractHttpMessageConverter<Integer> {

	private static final MediaType APPLICATION_PLUS_JSON = new MediaType("application", "*+json");

	public static final Charset DEFAULT_CHARSET = StandardCharsets.ISO_8859_1;

	@Nullable
	private volatile List<Charset> availableCharsets;

	@Setter
	private boolean writeAcceptCharset = false;

	public IntegerHttpMessageConverter() {
		this(DEFAULT_CHARSET);
	}

	public IntegerHttpMessageConverter(Charset defaultCharset) {
		super(defaultCharset, MediaType.TEXT_PLAIN, MediaType.ALL);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return Integer.class == clazz;
	}

	@Override
	protected Integer readInternal(Class<? extends Integer> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
		long length = inputMessage.getHeaders().getContentLength();
		byte[] bytes = (length >= 0 && length <= Integer.MAX_VALUE ?
				inputMessage.getBody().readNBytes((int) length) : inputMessage.getBody().readAllBytes());
		String str = new String(bytes, charset);
		return Integer.parseInt(str);
	}

	@Override
	protected Long getContentLength(Integer str, @Nullable MediaType contentType) {
		Charset charset = getContentTypeCharset(contentType);
		return (long) str.toString().getBytes(charset).length;
	}

	@Override
	protected void addDefaultHeaders(HttpHeaders headers, Integer s, @Nullable MediaType type) throws IOException {
		if (headers.getContentType() == null) {
			if (type != null && type.isConcrete() && (type.isCompatibleWith(MediaType.APPLICATION_JSON) ||
					type.isCompatibleWith(APPLICATION_PLUS_JSON))) {
				// Prevent charset parameter for JSON.
				headers.setContentType(type);
			}
		}
		super.addDefaultHeaders(headers, s, type);
	}

	@Override
	protected void writeInternal(Integer integer, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		HttpHeaders headers = outputMessage.getHeaders();
		if (this.writeAcceptCharset && headers.get(HttpHeaders.ACCEPT_CHARSET) == null) {
			headers.setAcceptCharset(getAcceptedCharsets());
		}
		Charset charset = getContentTypeCharset(headers.getContentType());
		StreamUtils.copy(integer.toString(), charset, outputMessage.getBody());
	}

	protected List<Charset> getAcceptedCharsets() {
		List<Charset> charsets = this.availableCharsets;
		if (charsets == null) {
			charsets = new ArrayList<>(Charset.availableCharsets().values());
			this.availableCharsets = charsets;
		}
		return charsets;
	}

	private Charset getContentTypeCharset(@Nullable MediaType contentType) {
		if (contentType != null) {
			Charset charset = contentType.getCharset();
			if (charset != null) {
				return charset;
			}
			else if (contentType.isCompatibleWith(MediaType.APPLICATION_JSON) ||
					contentType.isCompatibleWith(APPLICATION_PLUS_JSON)) {
				// Matching to AbstractJackson2HttpMessageConverter#DEFAULT_CHARSET
				return StandardCharsets.UTF_8;
			}
		}
		Charset charset = getDefaultCharset();
		Assert.state(charset != null, "No default charset");
		return charset;
	}

	@Override
	protected boolean supportsRepeatableWrites(Integer s) {
		return true;
	}
}
