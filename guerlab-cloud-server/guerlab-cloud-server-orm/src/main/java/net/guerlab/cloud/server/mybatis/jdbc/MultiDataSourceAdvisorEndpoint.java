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

package net.guerlab.cloud.server.mybatis.jdbc;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * 多数据源监控端点.
 *
 * @author guer
 */
@Slf4j
@Endpoint(id = "multi-datasource")
public class MultiDataSourceAdvisorEndpoint {

	private final MultiDataSourceBeanPostProcessor postProcessor;

	public MultiDataSourceAdvisorEndpoint(MultiDataSourceBeanPostProcessor postProcessor) {
		this.postProcessor = postProcessor;
	}

	/**
	 * 获取受代理的bean类名称列表.
	 *
	 * @return 受代理的bean类名称列表
	 */
	@SuppressWarnings("unused")
	@ReadOperation
	public Collection<String> getBeanClassNames() {
		return postProcessor.getBeanClassNames();
	}
}
