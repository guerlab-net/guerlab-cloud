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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

public class PdfHttpMessageConverter extends AbstractHttpMessageConverter<byte[]> {

	public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	public PdfHttpMessageConverter() {
		this(DEFAULT_CHARSET);
	}

	public PdfHttpMessageConverter(Charset defaultCharset) {
		super(defaultCharset, MediaType.APPLICATION_PDF);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return byte[].class == clazz;
	}

	@Override
	protected byte[] readInternal(Class<? extends byte[]> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return inputMessage.getBody().readAllBytes();
	}

	@Override
	protected Long getContentLength(byte[] str, @Nullable MediaType contentType) {
		return (long) str.length;
	}

	@Override
	protected void addDefaultHeaders(HttpHeaders headers, byte[] s, @Nullable MediaType type) throws IOException {
		headers.setContentType(MediaType.APPLICATION_PDF);
		super.addDefaultHeaders(headers, s, type);
	}

	@Override
	protected void writeInternal(byte[] bytes, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		StreamUtils.copy(bytes, outputMessage.getBody());
	}

	@Override
	protected boolean supportsRepeatableWrites(byte[] s) {
		return true;
	}
}
