package net.guerlab.cloud.searchparams;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author guer
 */
public class OrderByTest {

	static Stream<Arguments> arguments() {
		return Stream.of(
				Arguments.of("ID", true),
				Arguments.of("id", true),
				Arguments.of("user_name", true),
				Arguments.of(" user_name ", true),
				Arguments.of(" 'name' ", true),
				Arguments.of(" 'name ", false),
				Arguments.of(" name‘ ", false),
				Arguments.of(" ab'c ", false),
				Arguments.of(" name -- ", false),
				Arguments.of(" id and ", false),
				Arguments.of(" id desc ", false),
				Arguments.of(" a.b ", false),
				Arguments.of(" date() ", false)
		);
	}

	@ParameterizedTest
	@MethodSource("arguments")
	void test(String columnName, boolean accept) {
		OrderBy orderBy = OrderBy.builder().columnName(columnName).build();
		Assertions.assertEquals(accept, orderBy.accept());
	}
}
