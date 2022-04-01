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

package net.guerlab.cloud.api.feign;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;

import org.springframework.lang.Nullable;

import net.guerlab.cloud.core.result.ApplicationStackTrace;
import net.guerlab.cloud.core.result.RemoteException;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 失败解析.
 *
 * @author guer
 */
public final class FailParser {

	private FailParser() {

	}

	/**
	 * 解析异常.
	 *
	 * @param rootNode json节点
	 * @return 异常信息
	 */
	public static ApplicationException parse(JsonNode rootNode) {
		String message = getMessage(rootNode);
		List<ApplicationStackTrace> stackTraces = getStackTraces(rootNode);
		int errorCode = getErrorCode(rootNode);
		RemoteException remoteException = RemoteException.build(message, stackTraces);
		return new ApplicationException(message, remoteException, errorCode);
	}

	/**
	 * 解析message字段.
	 *
	 * @param rootNode 跟节点
	 * @return message字段
	 */
	private static String getMessage(JsonNode rootNode) {
		return StringUtils.trimToNull(rootNode.get(Constants.FIELD_MESSAGE).asText());
	}

	/**
	 * 解析错误编码.
	 *
	 * @param rootNode 跟节点
	 * @return 错误编码
	 */
	private static int getErrorCode(JsonNode rootNode) {
		if (!rootNode.has(Constants.FIELD_ERROR_CODE)) {
			return 0;
		}

		return rootNode.get(Constants.FIELD_ERROR_CODE).intValue();
	}

	/**
	 * 解析堆栈信息列表.
	 *
	 * @param rootNode 跟节点
	 * @return 堆栈信息列表
	 */
	private static List<ApplicationStackTrace> getStackTraces(JsonNode rootNode) {
		if (!rootNode.has(Constants.FIELD_STACK_TRACES)) {
			return Collections.emptyList();
		}

		JsonNode stackTraceNode = rootNode.get(Constants.FIELD_STACK_TRACES);

		if (!stackTraceNode.isArray()) {
			return Collections.emptyList();
		}

		return StreamSupport.stream(stackTraceNode.spliterator(), false).map(FailParser::parseApplicationStackTrace)
				.filter(Objects::nonNull).collect(Collectors.toList());
	}

	/**
	 * 解析应用堆栈信息.
	 *
	 * @param node 节点
	 * @return 应用堆栈信息
	 */
	@Nullable
	private static ApplicationStackTrace parseApplicationStackTrace(JsonNode node) {
		if (!node.has(Constants.FIELD_APPLICATION_NAME)) {
			return null;
		}

		ApplicationStackTrace applicationStackTrace = new ApplicationStackTrace();
		applicationStackTrace.setApplicationName(node.get(Constants.FIELD_APPLICATION_NAME).asText());

		JsonNode stackTraceNode = node.get(Constants.FIELD_STACK_TRACE);

		if (stackTraceNode == null || !stackTraceNode.isArray()) {
			applicationStackTrace.setStackTrace(Collections.emptyList());
		}
		else {
			applicationStackTrace.setStackTrace(
					StreamSupport.stream(stackTraceNode.spliterator(), false).map(JsonNode::asText)
							.collect(Collectors.toList()));
		}

		return applicationStackTrace;
	}
}
