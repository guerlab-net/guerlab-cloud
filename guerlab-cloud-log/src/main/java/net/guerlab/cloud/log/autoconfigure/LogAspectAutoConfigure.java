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
package net.guerlab.cloud.log.autoconfigure;

import net.guerlab.cloud.log.aspect.LogAspect;
import net.guerlab.cloud.log.handler.LogHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志切面自动配置
 *
 * @author guer
 */
@Configuration
public class LogAspectAutoConfigure {

    /**
     * 构造日志切面
     *
     * @param logHandlersProvider
     *         日志处理器列表提供者
     * @param messageSource
     *         messageSource
     * @return 日志切面
     */
    @Bean
    public LogAspect logAspect(ObjectProvider<LogHandler> logHandlersProvider, MessageSource messageSource) {
        return new LogAspect(logHandlersProvider, messageSource);
    }
}