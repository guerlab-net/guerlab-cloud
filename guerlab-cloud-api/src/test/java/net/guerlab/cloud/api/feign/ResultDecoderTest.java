package net.guerlab.cloud.api.feign;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author guer
 */
public class ResultDecoderTest {

	private final ResultDecoder decoder = new ResultDecoder(new ObjectMapper());

	static Stream<Arguments> testSourceProvider() {
		List<Arguments> arguments = new ArrayList<>();

		arguments.add(Arguments.arguments("abc", "abc", String.class));
		arguments.add(Arguments.arguments("abc", "{\"status\":true,\"errorCode\":0,\"data\":\"abc\"}", String.class));
		arguments.add(Arguments.arguments(1, "{\"status\":true,\"errorCode\":0,\"data\":\"1\"}", Integer.class));
		arguments.add(Arguments.arguments(1, "1", Integer.class));

		return arguments.stream();
	}

	@ParameterizedTest
	@MethodSource("testSourceProvider")
	void match(Object result, String resultBody, Type type) throws Exception {
		Response response = Response.builder().body(resultBody.getBytes()).request(request()).build();

		Assertions.assertEquals(result, decoder.decode(response, type));
	}

	private Request request() {
		return Request.create(Request.HttpMethod.GET, "http://127.0.0.1/", Collections.emptyMap(), new byte[] {}, StandardCharsets.UTF_8, new RequestTemplate());
	}
}
