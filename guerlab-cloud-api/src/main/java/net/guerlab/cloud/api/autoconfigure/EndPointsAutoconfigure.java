package net.guerlab.cloud.api.autoconfigure;

import net.guerlab.cloud.api.endpoints.FeignClientEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 监控端点自动配置
 *
 * @author guer
 */
@Configuration
public class EndPointsAutoconfigure {

    /**
     * 构造FeignClient实例监控端点
     *
     * @return FeignClient实例监控端点
     */
    @Bean
    public FeignClientEndpoint feignClientEndpoint() {
        return new FeignClientEndpoint();
    }
}
