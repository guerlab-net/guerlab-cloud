package net.guerlab.smart.platform.basic.gateway.gray;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 灰度控制配置
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "spring.cloud.gateway.gray")
public class GrayLoadBalancerClientFilterProperties {

    /**
     * 请求关键字
     */
    private String requestKey;

    /**
     * 元信息关键字
     */
    private String metadataKey;
}
