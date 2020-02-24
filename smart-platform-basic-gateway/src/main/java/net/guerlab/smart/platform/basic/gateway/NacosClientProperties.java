package net.guerlab.smart.platform.basic.gateway;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 认证配置
 *
 * @author guer
 */
@Data
@Component
@ConfigurationProperties(prefix = "nacos")
public class NacosClientProperties {

    /**
     * NACOS主机名称
     */
    private String hostname = "nacos";

    /**
     * NACOS端口
     */
    private Integer port = 8848;

    /**
     * 命名空间
     */
    private String namespace = "";
}
