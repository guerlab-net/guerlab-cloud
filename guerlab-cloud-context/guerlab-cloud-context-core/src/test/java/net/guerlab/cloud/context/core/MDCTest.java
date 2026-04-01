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

package net.guerlab.cloud.context.core;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import net.guerlab.cloud.core.Constants;

/**
 * @author guer
 */
class MDCTest {

	@Test
	void traceIdTest() {
		String tractId = UUID.randomUUID().toString();
		ContextAttributesHolder.get().put(Constants.REQUEST_TRACE_ID_HEADER, tractId);
		String result = MDC.get(Constants.TRACE_ID_KEY);
		Assertions.assertEquals(result, tractId);
	}
}
