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
package net.guerlab.cloud.auth.test.properties;

import net.guerlab.cloud.auth.properties.Rc4TokenFactoryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * rc4 token 配置
 *
 * @author guer
 */
@RefreshScope
@ConfigurationProperties(prefix = TestRc4TokenFactoryProperties.PREFIX)
public class TestRc4TokenFactoryProperties extends Rc4TokenFactoryProperties {

    public final static String PREFIX = "auth.test.token-factory.rc4";
}
