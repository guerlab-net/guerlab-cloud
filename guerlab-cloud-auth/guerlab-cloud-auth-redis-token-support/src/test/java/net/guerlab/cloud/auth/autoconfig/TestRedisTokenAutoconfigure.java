/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.auth.autoconfig;

import net.guerlab.cloud.auth.properties.TestRedisTokenFactoryProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 测试用redis token自动配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties({ TestRedisTokenFactoryProperties.class })
public class TestRedisTokenAutoconfigure {}
