package net.guerlab.cloud.log.autoconfigure;

import net.guerlab.cloud.log.aop.LogAop;
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
public class LogAopAutoConfigure {

    /**
     * 构造日志切换
     *
     * @param logHandlersProvider
     *         日志处理器列表提供者
     * @param messageSource
     *         messageSource
     * @return 日志切面
     */
    @Bean
    public LogAop logAop(ObjectProvider<LogHandler> logHandlersProvider, MessageSource messageSource) {
        return new LogAop(logHandlersProvider, messageSource);
    }
}
