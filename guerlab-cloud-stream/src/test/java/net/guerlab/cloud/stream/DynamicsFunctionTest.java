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

package net.guerlab.cloud.stream;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 动态方法测试.
 *
 * @author guer
 */
class DynamicsFunctionTest {

	AnnotationConfigApplicationContext context;

	@BeforeEach
	void init() {
		context = new AnnotationConfigApplicationContext();
	}

	@AfterEach
	void close() {
		Optional.ofNullable(context).ifPresent(AnnotationConfigApplicationContext::close);
	}

	@Test
	void singleParameterTest() {
		test0("net.guerlab.cloud.stream.SingleParameterEvent", SingleParameterEventListener.class);
	}

	@Test
	void doubleParameterTest() {
		test0("net.guerlab.cloud.stream.DoubleParameterEvent", DoubleParameterEventListener.class);
	}

	void test0(String eventClass, Class<?> listenerClass) {
		TestPropertyValues.of("spring.cloud.function.dynamics.definitions[0].function-name=test").applyTo(context);
		TestPropertyValues.of("spring.cloud.function.dynamics.definitions[0].input-class=java.lang.String")
				.applyTo(context);
		TestPropertyValues.of("spring.cloud.function.dynamics.definitions[0].event-class=" + eventClass)
				.applyTo(context);
		context.registerBean(listenerClass.getSimpleName(), listenerClass);
		context.register(DynamicsFunctionAutoConfigure.class);
		context.refresh();

		Map<String, DynamicsFunction> dynamicsFunctionMap = context.getBeansOfType(DynamicsFunction.class);
		for (DynamicsFunction dynamicsFunction : dynamicsFunctionMap.values()) {
			Assertions.assertThrowsExactly(DynamicsFunctionTestException.class, () -> dynamicsFunction.invoke("inputTest"));
		}
	}
}
