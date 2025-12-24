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

package net.guerlab.cloud.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 常量.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class Constants {

	/**
	 * 默认上级ID.
	 */
	public static final Long DEFAULT_PARENT_ID = 0L;

	/**
	 * 默认排序值.
	 */
	public static final Long DEFAULT_ORDER_NUM = 0L;

	/**
	 * TOKEN.
	 */
	public static final String TOKEN = "Authorization";

	/**
	 * 空ID.
	 */
	public static final Long EMPTY_ID = 0L;

	/**
	 * 空名称.
	 */
	public static final String EMPTY_NAME = "";

	/**
	 * 请求方法.
	 */
	public static final String REQUEST_METHOD = "Request-Method";

	/**
	 * 请求URI.
	 */
	public static final String REQUEST_URI = "Request-Uri";

	/**
	 * 完整请求路径.
	 */
	public static final String COMPLETE_REQUEST_URI = "Complete-Request-Uri";

	/**
	 * 当前操作者.
	 */
	public static final String CURRENT_OPERATOR = "currentOperator";

	/**
	 * 当前操作者-请求头.
	 */
	public static final String CURRENT_OPERATOR_HEADER = "X-Current-Operator";

	/**
	 * 最大日期.
	 */
	public static final LocalDate MAX_DATE = LocalDate.of(9999, 12, 31);

	/**
	 * 最大日期时间.
	 */
	public static final LocalDateTime MAX_DATETIME = LocalDateTime.of(MAX_DATE, LocalTime.MAX);

	/**
	 * 允许传递的header前缀.
	 */
	public static final String ALLOW_TRANSFER_HEADER_PREFIX = "X-Transfer-Inside-";

	/**
	 * 请求头-是否包装响应.
	 */
	public static final String HTTP_HEADER_RESPONSE_WRAPPED = "X-Response-Wrapped";

	/**
	 * 请求头-请求源.
	 */
	public static final String HTTP_HEADER_REQUEST_SOURCE = "X-Request-Source";

	private Constants() {
	}
}
