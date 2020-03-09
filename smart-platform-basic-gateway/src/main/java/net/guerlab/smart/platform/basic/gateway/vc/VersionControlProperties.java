package net.guerlab.smart.platform.basic.gateway.vc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 版本控制配置
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "spring.cloud.gateway.version-control")
public class VersionControlProperties {

    /**
     * 请求关键字
     */
    private String requestKey;

    /**
     * 元信息关键字
     */
    private String metadataKey;
}
