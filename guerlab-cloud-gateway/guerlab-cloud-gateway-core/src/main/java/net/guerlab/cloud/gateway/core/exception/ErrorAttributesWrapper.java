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

package net.guerlab.cloud.gateway.core.exception;

import java.util.Map;

import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * 错误信息包装处理器.
 *
 * @author guer
 */
public interface ErrorAttributesWrapper {

	/**
	 * 包装处理.
	 *
	 * @param request         请求
	 * @param errorAttributes 错误信息
	 * @return 包装后的错误信息
	 */
	Map<String, Object> wrapper(ServerRequest request, Map<String, Object> errorAttributes);
}
