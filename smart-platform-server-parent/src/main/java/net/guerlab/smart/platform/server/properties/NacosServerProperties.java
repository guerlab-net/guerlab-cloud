package net.guerlab.smart.platform.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * NACOS服务配置
 *
 * @author guer
 */
@Data
@ConfigurationProperties(prefix = "nacos")
public class NacosServerProperties {

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

    /**
     * 多应用名称列表
     */
    private List<String> appNames;
}
