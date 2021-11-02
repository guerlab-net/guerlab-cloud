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
package net.guerlab.cloud.auth.webflux.test;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 授权配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties({ TestJwtTokenFactoryProperties.class, TestAuthWebProperties.class })
public class TestAuthAutoconfigure {

    @Bean
    public TestJwtTokenFactory testJwtTokenFactory(TestJwtTokenFactoryProperties properties) {
        TestJwtTokenFactory factory = new TestJwtTokenFactory();
        factory.setProperties(properties);
        return factory;
    }

    @Bean
    public TestTokenHandlerFilter testTokenHandlerFilter(TestAuthWebProperties authProperties,
            TestJwtTokenFactory tokenFactory) {
        return new TestTokenHandlerFilter(authProperties, tokenFactory);
    }

    @Bean
    public TestTokenAfterHandlerFilter testTokenAfterHandlerFilter() {
        return new TestTokenAfterHandlerFilter();
    }
}
