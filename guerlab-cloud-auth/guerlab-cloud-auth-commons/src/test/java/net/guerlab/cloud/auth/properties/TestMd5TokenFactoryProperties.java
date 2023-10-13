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

package net.guerlab.cloud.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * md5 token 配置.
 *
 * @author guer
 */
@RefreshScope
@ConfigurationProperties(prefix = TestMd5TokenFactoryProperties.PREFIX)
public class TestMd5TokenFactoryProperties extends Md5TokenFactoryProperties {

	/**
	 * 配置前缀.
	 */
	public static final String PREFIX = "auth.test.token-factory.md5";
}
