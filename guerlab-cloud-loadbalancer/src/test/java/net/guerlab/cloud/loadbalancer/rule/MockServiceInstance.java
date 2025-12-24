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

package net.guerlab.cloud.loadbalancer.rule;

import java.net.URI;
import java.util.Map;

import lombok.Data;

import org.springframework.cloud.client.ServiceInstance;

/**
 * 模拟服务实例.
 *
 * @author guer
 */
@Data
public class MockServiceInstance implements ServiceInstance {

	private final String serviceId;

	private final Map<String, String> metadata;

	public MockServiceInstance(String serviceId, Map<String, String> metadata) {
		this.serviceId = serviceId;
		this.metadata = metadata;
	}

	@Override
	public String getServiceId() {
		return serviceId;
	}

	@Override
	public String getHost() {
		return "localhost";
	}

	@Override
	public int getPort() {
		return 0;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public URI getUri() {
		return URI.create("http://localhost/mockPath");
	}

	@Override
	public Map<String, String> getMetadata() {
		return metadata;
	}
}
