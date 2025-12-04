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

package net.guerlab.cloud.server.mybatis.exception.handler.builder;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.MyBatisSystemException;

import net.guerlab.cloud.commons.exception.handler.AbstractResponseBuilder;
import net.guerlab.cloud.core.result.Fail;

/**
 * TooManyResultsException异常处理.
 *
 * @author guer
 */
public class MybatisSystemExceptionResponseBuilder extends AbstractResponseBuilder {

	private static final Map<Class<? extends Throwable>, Class<? extends AbstractResponseBuilder>> BUILDER_MAP = new HashMap<>();

	/**
	 * 添加异常对应的异常信息构建器.
	 *
	 * @param causeClass   异常类型
	 * @param builderClass 异常信息构建器类型
	 */
	public static void addBuilder(Class<? extends Throwable> causeClass, Class<? extends AbstractResponseBuilder> builderClass) {
		BUILDER_MAP.put(causeClass, builderClass);
	}

	@Override
	public boolean accept(Throwable e) {
		return e instanceof MyBatisSystemException;
	}

	@Override
	public Fail<?> build(Throwable e) {
		MyBatisSystemException exception = (MyBatisSystemException) e;
		Throwable cause = exception.getCause();

		for (Map.Entry<Class<? extends Throwable>, Class<? extends AbstractResponseBuilder>> entry : BUILDER_MAP.entrySet()) {
			if (entry.getKey().isInstance(cause)) {
				try {
					AbstractResponseBuilder builder = entry.getValue().getDeclaredConstructor().newInstance();
					builder.setMessageSource(messageSource);
					builder.setStackTracesHandler(stackTracesHandler);

					return builder.build(cause);
				}
				catch (Exception ignored) {
				}
			}
		}

		if (cause.getCause() != null) {
			cause = cause.getCause();
		}

		String message = null;
		if (cause != null) {
			message = cause.getMessage();
		}
		if (message == null) {
			message = exception.getMessage();
		}

		Fail<Void> fail = new Fail<>(message);
		stackTracesHandler.setStackTrace(fail, e);
		return fail;
	}
}

