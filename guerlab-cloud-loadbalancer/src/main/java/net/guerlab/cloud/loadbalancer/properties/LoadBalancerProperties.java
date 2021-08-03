package net.guerlab.cloud.loadbalancer.properties;

import lombok.Data;
import net.guerlab.cloud.loadbalancer.Constants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 负载均衡配置
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = LoadBalancerProperties.PROPERTIES_PREFIX)
public class LoadBalancerProperties {

    /**
     * 配置前缀
     */
    public static final String PROPERTIES_PREFIX = Constants.PROPERTIES_PREFIX;

    /**
     * 未匹配的时候返回空可用服务集合
     */
    private boolean noMatchReturnEmpty = false;

}
