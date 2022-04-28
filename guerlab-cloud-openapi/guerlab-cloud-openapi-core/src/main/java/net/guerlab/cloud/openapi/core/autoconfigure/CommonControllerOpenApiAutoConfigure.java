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

package net.guerlab.cloud.openapi.core.autoconfigure;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springdoc.core.SpringDocConfigProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import static org.springdoc.core.Constants.SPRINGDOC_ENABLED;

/**
 * 公共控制器接口文档自动配置.
 *
 * @author guer
 */
@Configuration
@ConditionalOnProperty(name = SPRINGDOC_ENABLED, matchIfMissing = true)
public class CommonControllerOpenApiAutoConfigure {

	private final SpringDocConfigProperties springDocConfigProperties;

	public CommonControllerOpenApiAutoConfigure(SpringDocConfigProperties springDocConfigProperties) {
		this.springDocConfigProperties = springDocConfigProperties;
	}

	@PostConstruct
	public void init() {
		springDocConfigProperties.addGroupConfig(buildCommonGroup());
	}

	public SpringDocConfigProperties.GroupConfig buildCommonGroup() {
		SpringDocConfigProperties.GroupConfig config = new SpringDocConfigProperties.GroupConfig();
		config.setGroup("common");
		config.setPackagesToScan(Collections.singletonList("net.guerlab.cloud.web.core.controller"));
		return config;
	}
}
