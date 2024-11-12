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

package net.guerlab.cloud.web.core.exception.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.http.HttpStatus;

import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.web.core.exception.DefaultExceptionInfo;
import net.guerlab.cloud.web.core.properties.GlobalExceptionProperties;

/**
 * @author guer
 */
class ThrowableResponseBuilderTest {

	private static GlobalExceptionProperties properties;

	private static ThrowableResponseBuilder builder;

	@BeforeAll
	static void init() {
		properties = new GlobalExceptionProperties();

		properties.setRewriteNonApplicationExceptions(true);
		properties.addOverrideExceptionTemplate(NullPointerException.class, "空指针异常:%s");

		builder = new ThrowableResponseBuilder(properties);
		builder.setStackTracesHandler(new DefaultStackTracesHandler(properties));
		builder.setMessageSource(new DelegatingMessageSource());
	}

	@Test
	void throwResponseStatusException() {
		ResponseStatusException e = new ResponseStatusException();
		Fail<Void> fail = builder.build(e);
		Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), fail.getErrorCode());
		Assertions.assertEquals("not_found", fail.getMessage());
	}

	@Test
	void throwNullPointerException() {
		NullPointerException e = new NullPointerException("npe_test");
		Fail<Void> fail = builder.build(e);
		Assertions.assertEquals(0, fail.getErrorCode());
		Assertions.assertEquals("空指针异常:npe_test", fail.getMessage());
	}

	@Test
	void throwIllegalAccessError() {
		IllegalAccessError e = new IllegalAccessError();
		Fail<Void> fail = builder.build(e);
		Assertions.assertEquals(DefaultExceptionInfo.DEFAULT_ERROR_CODE, fail.getErrorCode());
		Assertions.assertEquals(DefaultExceptionInfo.DEFAULT_MSG, fail.getMessage());
	}

	@Test
	void throwIllegalAccessErrorWithNotOverride() {
		properties.setRewriteNonApplicationExceptions(false);
		IllegalAccessError e = new IllegalAccessError("IllegalAccessError");
		Fail<Void> fail = builder.build(e);
		Assertions.assertEquals(0, fail.getErrorCode());
		Assertions.assertEquals("IllegalAccessError", fail.getMessage());
		properties.setRewriteNonApplicationExceptions(true);
	}
}
