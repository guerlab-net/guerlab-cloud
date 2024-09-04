/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

import java.nio.charset.StandardCharsets;

import feign.Response;
import feign.Util;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import net.guerlab.cloud.commons.exception.LoadBalancerServiceInstanceUnavailableException;

/**
 * 负载均衡未发现实例失败响应解析.
 *
 * @author guer
 */
@Slf4j
public class LoadbalancerNotContainInstanceResponseDecoder implements OrderedErrorDecoder {

	private static final String PREFIX = "Load balancer does not contain an instance for the service ";

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

	@Nullable
	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.body() == null) {
			return null;
		}

		try {
			String resultBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
			if (!resultBody.startsWith(PREFIX)) {
				return null;
			}

			String serviceId = resultBody.substring(PREFIX.length());

			return new LoadBalancerServiceInstanceUnavailableException(serviceId);
		}
		catch (Exception e) {
			log.debug(e.getLocalizedMessage(), e);
			return null;
		}
	}
}
