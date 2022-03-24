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

package net.guerlab.cloud.core.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.guerlab.cloud.core.properties.SequenceProperties;
import net.guerlab.cloud.core.sequence.Sequence;

/**
 * 分布式序列自动配置.
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties(SequenceProperties.class)
public class SequenceAutoconfigure {

	/**
	 * 分布式序列自动配置.
	 *
	 * @param properties
	 *         分布式序列自动配置属性
	 * @return 分布式序列
	 */
	@Bean
	@RefreshScope
	public Sequence sequence(SequenceProperties properties) {
		return new Sequence(properties.getWorkerId(), properties.getDataCenterId());
	}
}
