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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import net.guerlab.cloud.core.Constants;

/**
 * @author guer
 */
class MDCTest {

	@Test
	void traceIdTest() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

		loggerContext.stop();
		loggerContext.getLoggerList().forEach(Logger::detachAndStopAllAppenders);

		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(loggerContext);
		encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] [traceId=%X{traceId}] [x-transfer-inside-trace-id=%X{x-transfer-inside-trace-id}]  %-5level %logger{36} - %msg%n");
		encoder.start();

		ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
		consoleAppender.setContext(loggerContext);
		consoleAppender.setName("CONSOLE");
		consoleAppender.setEncoder(encoder);
		consoleAppender.start();

		Logger logger = loggerContext.getLogger(this.getClass());
		logger.addAppender(consoleAppender);
		logger.setLevel(Level.DEBUG);

		loggerContext.start();

		String tractId = UUID.randomUUID().toString();
		ContextAttributesHolder.get().put(Constants.REQUEST_TRACE_ID_HEADER, tractId);
		String result = MDC.get(Constants.MDC_TRACE_ID_KEY);
		logger.info("log message");
		Assertions.assertEquals(result, tractId);
	}
}
