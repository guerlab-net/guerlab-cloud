package net.guerlab.smart.platform.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Collections;
import java.util.List;

/**
 * 开发模式配置
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = DebugProperties.PROPERTIES_PREFIX)
public class DebugProperties {

    /**
     * 配置前缀
     */
    public static final String PROPERTIES_PREFIX = "spring.cloud.debug";

    /**
     * 启用标志
     */
    private boolean enable;

    /**
     * 网关代理地址
     */
    private String gatewayProxyUrl = "http://gateway/proxy/";

    /**
     * 代理请求头名
     */
    private String proxyHeaderName = "GUERLAB-PROXY";

    /**
     * 代理请求头值
     */
    private String proxyHeaderValue = "GUERLAB-PROXY";

    /**
     * 本地服务列表
     */
    private List<String> localServices = Collections.emptyList();
}
