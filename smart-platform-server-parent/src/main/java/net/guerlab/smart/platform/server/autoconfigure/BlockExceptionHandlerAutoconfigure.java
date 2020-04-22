package net.guerlab.smart.platform.server.autoconfigure;

import net.guerlab.smart.platform.server.sentinel.CustomerBlockExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义限流处理自动配置
 *
 * @author guer
 */
@Configuration
public class BlockExceptionHandlerAutoconfigure {

    /**
     * 构造自定义限流处理
     *
     * @return 自定义限流处理
     */
    @Bean
    public CustomerBlockExceptionHandler customerBlockExceptionHandler() {
        return new CustomerBlockExceptionHandler();
    }
}
