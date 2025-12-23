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

package net.guerlab.cloud.gateway.core.base;

import java.util.Collections;
import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * cors配置.
 *
 * @author guer
 */
@Data
@ConfigurationProperties("spring.cloud.gateway.cors")
public class CorsProperties {

	/**
	 * 允许暴露头部列表.
	 */
	private List<String> accessControlExposeHeaders = Collections.emptyList();
}
