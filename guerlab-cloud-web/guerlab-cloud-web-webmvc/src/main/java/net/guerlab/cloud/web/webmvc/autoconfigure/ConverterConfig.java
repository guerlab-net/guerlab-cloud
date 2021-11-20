/*
 * Copyright 2018-2021 guerlab.net and other contributors.
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
package net.guerlab.cloud.web.webmvc.autoconfigure;

import net.guerlab.cloud.core.converter.AutoLoadConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * 转换配置
 *
 * @author guer
 */
@Configuration
@ConditionalOnBean(RequestMappingHandlerAdapter.class)
public class ConverterConfig {

    /**
     * 自动加载转换器
     *
     * @param handlerAdapter
     *         RequestMappingHandlerAdapter
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void addConverter(RequestMappingHandlerAdapter handlerAdapter) {
        WebBindingInitializer webBindingInitializer = handlerAdapter.getWebBindingInitializer();
        if (!(webBindingInitializer instanceof ConfigurableWebBindingInitializer)) {
            return;
        }

        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) webBindingInitializer;
        ConversionService conversionService = initializer.getConversionService();
        if (initializer.getConversionService() == null || !(conversionService instanceof GenericConversionService)) {
            return;
        }

        GenericConversionService service = (GenericConversionService) conversionService;
        StreamSupport.stream(ServiceLoader.load(AutoLoadConverter.class).spliterator(), false)
                .forEach(service::addConverter);
    }
}
