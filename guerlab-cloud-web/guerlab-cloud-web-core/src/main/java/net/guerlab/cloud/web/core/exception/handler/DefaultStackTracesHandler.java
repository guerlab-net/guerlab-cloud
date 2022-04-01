/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.commons.config.GlobalExceptionConfig;
import net.guerlab.cloud.commons.exception.handler.StackTracesHandler;
import net.guerlab.cloud.core.result.ApplicationStackTrace;
import net.guerlab.cloud.core.result.Fail;
import net.guerlab.cloud.core.result.RemoteException;
import net.guerlab.cloud.core.util.SpringUtils;

/**
 * 默认堆栈处理.
 *
 * @author guer
 */
public class DefaultStackTracesHandler implements StackTracesHandler {

	private final GlobalExceptionConfig config;

	/**
	 * 通过全局异常处理配置初始化.
	 *
	 * @param config 全局异常处理配置
	 */
	public DefaultStackTracesHandler(GlobalExceptionConfig config) {
		this.config = config;
	}

	@Override
	public void setStackTrace(Fail<?> fail, @Nullable Throwable throwable) {
		if (throwable == null || !config.isPrintStackTrace()) {
			return;
		}

		fail.setStackTraces(getStackTraces(throwable));
	}

	/**
	 * 获取应用堆栈跟踪列表.
	 *
	 * @param throwable 异常
	 * @return 应用堆栈跟踪列表
	 */
	protected List<ApplicationStackTrace> getStackTraces(@Nullable Throwable throwable) {
		if (throwable == null) {
			return Collections.emptyList();
		}

		List<ApplicationStackTrace> stackTraces = new ArrayList<>();
		setSubStackTrace(stackTraces, throwable);
		return stackTraces;
	}

	/**
	 * 设置内部应用堆栈.
	 *
	 * @param stackTraces 应用堆栈跟踪列表
	 * @param throwable   异常
	 */
	protected void setSubStackTrace(List<ApplicationStackTrace> stackTraces, @Nullable Throwable throwable) {
		if (throwable == null) {
			return;
		}
		setSubStackTrace(stackTraces, throwable.getCause());

		if (throwable instanceof RemoteException) {
			stackTraces.add(((RemoteException) throwable).getApplicationStackTrace());
		}
		else {
			ApplicationStackTrace applicationStackTrace = new ApplicationStackTrace();
			applicationStackTrace.setApplicationName(SpringUtils.getApplicationName());
			applicationStackTrace.setStackTrace(
					Arrays.stream(throwable.getStackTrace()).map(this::buildStackTraceElementText)
							.filter(Objects::nonNull).collect(Collectors.toList()));

			stackTraces.add(applicationStackTrace);
		}
	}

	/**
	 * 构建.
	 *
	 * @param element 堆栈跟踪元素
	 * @return 堆栈跟踪元素文本
	 */
	@Nullable
	protected String buildStackTraceElementText(StackTraceElement element) {
		String methodKey = element.getClassName() + "." + element.getMethodName();

		if (config.excludeMatch(methodKey) && !config.includeMatch(methodKey)) {
			return null;
		}

		return methodKey + ":" + element.getLineNumber();
	}
}
