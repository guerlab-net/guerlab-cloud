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

package net.guerlab.cloud.api.feign;

import feign.Response;
import jakarta.annotation.Nullable;

import org.springframework.core.Ordered;

/**
 * 可排序错误解析.
 *
 * @author guer
 */
public interface OrderedErrorDecoder extends Ordered {

	/**
	 * Implement this method in order to decode an HTTP {@link Response} when
	 * {@link Response#status()} is not in the 2xx range. Please raise application-specific exceptions
	 * where possible. If your exception is retryable, wrap or subclass {@link feign.RetryableException}
	 *
	 * @param methodKey {@link feign.Feign#configKey} of the java method that invoked the request. ex.
	 *                  {@code IAM#getUser()}
	 * @param response  HTTP response where {@link Response#status() status} is greater than or equal
	 *                  to {@code 300}.
	 * @return Exception IOException, if there was a network error reading the response or an
	 * application-specific exception decoded by the implementation. If the throwable is
	 * retryable, it should be wrapped, or a subtype of {@link feign.RetryableException}
	 */
	@Nullable
	Exception decode(String methodKey, Response response);
}
