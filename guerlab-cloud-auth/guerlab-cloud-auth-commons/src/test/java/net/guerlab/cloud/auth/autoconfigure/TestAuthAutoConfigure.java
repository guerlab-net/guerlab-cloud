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

package net.guerlab.cloud.auth.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import net.guerlab.cloud.auth.properties.TestJwtTokenFactoryProperties;
import net.guerlab.cloud.auth.properties.TestMd5TokenFactoryProperties;
import net.guerlab.cloud.auth.properties.TestRc4TokenFactoryProperties;

/**
 * 授权配置.
 *
 * @author guer
 */
@AutoConfiguration
@EnableConfigurationProperties({TestJwtTokenFactoryProperties.class, TestMd5TokenFactoryProperties.class,
		TestRc4TokenFactoryProperties.class})
public class TestAuthAutoConfigure { }
