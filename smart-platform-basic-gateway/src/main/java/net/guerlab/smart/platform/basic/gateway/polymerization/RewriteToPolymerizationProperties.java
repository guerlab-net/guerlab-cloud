package net.guerlab.smart.platform.basic.gateway.polymerization;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 重新请求至聚合服务配置
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "spring.cloud.gateway.rewrite-to-polymerization")
public class RewriteToPolymerizationProperties implements IRewriteToPolymerizationProperties {

    /**
     * 是否启用
     */
    private Boolean enable = false;

    /**
     * 聚合服务名称列表
     */
    private List<String> serviceNames = Collections.singletonList("polymerization-web");

    /**
     * 单服务配置表
     */
    private Map<String, RewriteToPolymerizationInstanceProperties> serviceProperties = new HashMap<>();

    /**
     * 根据服务名获取配置
     *
     * @param serviceId
     *         服务名
     * @return 配置
     */
    public IRewriteToPolymerizationProperties getProperties(String serviceId) {
        if (serviceProperties == null || StringUtils.isBlank(serviceId)) {
            return this;
        }

        IRewriteToPolymerizationProperties properties = serviceProperties.get(serviceId.trim());
        return properties == null ? this : properties;
    }
}
