package net.guerlab.cloud.context.webflux.autoconfigure;

import net.guerlab.cloud.context.webflux.filter.ContextAttributesServerWebExchangeDecoratorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 上下文属性包装器处理自动配置
 *
 * @author guer
 */
@Configuration
public class ContextAttributesServerWebExchangeDecoratorFilterAutoconfigure {

    /**
     * 创建上下文属性包装器处理
     *
     * @return 上下文属性包装器处理
     */
    @Bean
    public ContextAttributesServerWebExchangeDecoratorFilter contextAttributesServerWebExchangeDecoratorFilter() {
        return new ContextAttributesServerWebExchangeDecoratorFilter();
    }
}
