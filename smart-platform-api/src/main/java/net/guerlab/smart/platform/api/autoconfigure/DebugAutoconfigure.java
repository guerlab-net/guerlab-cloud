package net.guerlab.smart.platform.api.autoconfigure;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.smart.platform.api.debug.DebugProxyRequestInterceptor;
import net.guerlab.smart.platform.api.properties.DebugProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 测试环境配置
 *
 * @author guer
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DebugProperties.class)
@ConditionalOnProperty(prefix = DebugProperties.PROPERTIES_PREFIX, value = "enable", havingValue = "true")
public class DebugAutoconfigure {

    @Bean
    public RequestInterceptor debugProxyRequestInterceptor(DebugProperties properties) {
        return new DebugProxyRequestInterceptor(properties);
    }

}
