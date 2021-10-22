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
package net.guerlab.cloud.auth.webmvc.test;

import net.guerlab.cloud.auth.webmvc.autoconfig.AbstractAuthInterceptorAutoconfigure;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * 授权配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties({ TestJwtTokenFactoryProperties.class, TestAuthWebProperties.class })
public class TestAuthAutoconfigure extends AbstractAuthInterceptorAutoconfigure<TestAuthWebProperties> {

    private final TestTokenHandlerAfterInterceptor tokenHandlerAfterInterceptor = new TestTokenHandlerAfterInterceptor();

    private final TestJwtTokenFactory tokenFactory;

    public TestAuthAutoconfigure(TestJwtTokenFactoryProperties properties) {
        TestJwtTokenFactory factory = new TestJwtTokenFactory();
        factory.setProperties(properties);
        this.tokenFactory = factory;
    }

    @Bean
    public TestJwtTokenFactory testJwtTokenFactory() {
        return tokenFactory;
    }

    @Bean
    public TestTokenHandlerInterceptor testTokenHandlerInterceptor() {
        return new TestTokenHandlerInterceptor(tokenFactory);
    }

    @Bean
    public TestTokenHandlerAfterInterceptor testTokenHandlerAfterInterceptor() {
        return tokenHandlerAfterInterceptor;
    }

    @Override
    protected void addInterceptorsInternal(InterceptorRegistry registry) {
        setPathPatterns(registry.addInterceptor(tokenHandlerAfterInterceptor));
    }
}
