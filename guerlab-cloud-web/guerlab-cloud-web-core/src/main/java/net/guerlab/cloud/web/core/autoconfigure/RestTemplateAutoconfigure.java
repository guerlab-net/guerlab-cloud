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
package net.guerlab.cloud.web.core.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.spring.commons.autoconfigure.ObjectMapperAutoconfigure;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * RestTemplate自动配置
 *
 * @author guer
 */
@Configuration
@AutoConfigureAfter(ObjectMapperAutoconfigure.class)
public class RestTemplateAutoconfigure {

    /**
     * 初始化LoadBalancedRestTemplate
     *
     * @param objectMapper
     *         objectMapper
     * @return LoadBalancedRestTemplate
     */
    @Bean
    @LoadBalanced
    @ConditionalOnBean(ObjectMapper.class)
    @ConditionalOnClass({ LoadBalanced.class, RestTemplate.class })
    @ConditionalOnMissingBean(value = RestTemplate.class, annotation = LoadBalanced.class)
    public RestTemplate loadBalancedRestTemplate(ObjectMapper objectMapper) {
        return createRestTemplate(objectMapper);
    }

    /**
     * 初始化RestTemplate
     *
     * @param objectMapper
     *         objectMapper
     * @return RestTemplate
     */
    @Bean
    @Primary
    @ConditionalOnBean(ObjectMapper.class)
    @ConditionalOnClass(RestTemplate.class)
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(ObjectMapper objectMapper) {
        return createRestTemplate(objectMapper);
    }

    private RestTemplate createRestTemplate(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setMessageConverters(
                Arrays.asList(new MappingJackson2HttpMessageConverter(objectMapper), new StringHttpMessageConverter(),
                        new FormHttpMessageConverter()));

        return restTemplate;
    }
}
