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

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import net.guerlab.cloud.api.autoconfigure.FeignAutoConfigure;
import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.core.autoconfigure.ObjectMapperAutoConfigure;
import net.guerlab.cloud.core.result.Result;
import net.guerlab.commons.exception.ApplicationException;

/**
 * @author guer
 */
@SpringBootTest(
		classes = {
				HttpMessageConvertersAutoConfiguration.class,
				JacksonAutoConfiguration.class,
				FeignAutoConfigure.class,
				FeignAutoConfiguration.class,
				FeignClientsConfiguration.class,
				ObjectMapperAutoConfigure.class,
				TestAutoConfigure.class
		},
		webEnvironment = SpringBootTest.WebEnvironment.NONE,
		properties = {
				"spring.cloud.polaris.config.enabled=false"
		}
)
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DecoderTest {

	@Resource
	private Decoder decoder;

	@Test
	@Order(0)
	void decoderTypeCheck() {
		Assertions.assertInstanceOf(DecoderWrapper.class, decoder);
	}

	@Test
	@Order(1)
	void stringToString() throws Exception {
		String target = "abc";
		String result = getResult("""
				abc""".getBytes(), String.class, Map.of(
				HttpHeaders.CONTENT_TYPE, List.of(MediaType.TEXT_PLAIN_VALUE)
		));
		Assertions.assertEquals(target, result);
	}

	@Test
	@Order(2)
	void stringToInteger() throws Exception {
		Integer target = 1;
		Integer result = getResult("""
				1""".getBytes(), Integer.class, Map.of(
				HttpHeaders.CONTENT_TYPE, List.of(MediaType.TEXT_PLAIN_VALUE)
		));
		Assertions.assertEquals(target, result);
	}

	@Test
	@Order(3)
	void resultToString() throws Exception {
		String target = "abc";
		String result = getResult("""
				{"status":true,"errorCode":0,"data":"abc"}""".getBytes(), String.class, Map.of(
				Constants.HTTP_HEADER_RESPONSE_WRAPPED, List.of("true"),
				HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE)
		));
		Assertions.assertEquals(target, result);
	}

	@Test
	@Order(4)
	void resultToInteger() throws Exception {
		Integer target = 1;
		Integer result = getResult("""
				{"status":true,"errorCode":0,"data":"1"}""".getBytes(), Integer.class, Map.of(
				Constants.HTTP_HEADER_RESPONSE_WRAPPED, List.of("true"),
				HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE)
		));
		Assertions.assertEquals(target, result);
	}

	@Test
	@Order(5)
	void resultToResult() throws Exception {
		Result<Integer> target = new Result<>();
		target.setStatus(true);
		target.setData(1);

		TypeReference<Result<Integer>> typeReference = new TypeReference<>() { };

		Result<Integer> result = getResult("""
				{"status":true,"errorCode":0,"data":"1"}""".getBytes(), typeReference.getType(), Map.of(
				HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE)
		));
		Assertions.assertEquals(target, result);
	}

	@Test
	@Order(6)
	void bytesToBytes() throws Exception {
		File emptyPdfFile = new File("src/test/resources", "empty_pdf.pdf");
		byte[] target = StreamUtils.copyToByteArray(new FileInputStream(emptyPdfFile));

		TypeReference<byte[]> typeReference = new TypeReference<>() { };

		byte[] result = getResult(target, typeReference.getType(), Map.of(
				HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_PDF_VALUE)
		));
		Assertions.assertArrayEquals(target, result);
	}

	@Test
	@Order(7)
	void resultParseError() {
		TypeReference<Result<Integer>> typeReference = new TypeReference<>() { };

		Exception exception = Assertions.assertThrows(ApplicationException.class, () -> getResult("""
				{"status":false,"errorCode":0,"message":"error_message"}""".getBytes(), typeReference.getType(), Map.of(
				HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE)
		)));

		Assertions.assertInstanceOf(ApplicationException.class, exception);
	}

	@Test
	@Order(8)
	void resultParseErrorAndResponseWrapper() {
		TypeReference<Result<Integer>> typeReference = new TypeReference<>() { };

		Exception exception = Assertions.assertThrows(ApplicationException.class, () -> getResult("""
				{"status":false,"errorCode":0,"message":"error_message"}""".getBytes(), typeReference.getType(), Map.of(
				Constants.HTTP_HEADER_RESPONSE_WRAPPED, List.of("true"),
				HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE)
		)));

		Assertions.assertInstanceOf(ApplicationException.class, exception);
	}

	@SuppressWarnings("unchecked")
	<T> T getResult(byte[] bodyBytes, Type type, Map<String, Collection<String>> headers) throws Exception {
		Response response = Response.builder().body(bodyBytes).request(request()).headers(headers)
				.status(200).build();
		Object result = decoder.decode(response, type);
		return (T) result;
	}

	private Request request() {
		return Request.create(Request.HttpMethod.GET, "http://127.0.0.1/", Collections.emptyMap(), new byte[] {}, StandardCharsets.UTF_8, new RequestTemplate());
	}
}
