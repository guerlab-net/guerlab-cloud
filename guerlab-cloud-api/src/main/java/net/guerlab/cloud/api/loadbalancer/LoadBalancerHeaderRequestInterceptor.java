package net.guerlab.cloud.api.loadbalancer;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.loadbalancer.properties.VersionControlProperties;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 负载均衡版本控制请求头注入拦截器
 *
 * @author guer
 */
@Slf4j
public class LoadBalancerHeaderRequestInterceptor implements RequestInterceptor {

    /**
     * 版本控制配置
     */
    private final VersionControlProperties properties;

    /**
     * 初始化负载均衡版本控制请求头注入拦截器
     *
     * @param properties
     *         版本控制配置
     */
    public LoadBalancerHeaderRequestInterceptor(VersionControlProperties properties) {
        this.properties = properties;
    }

    @Override
    public void apply(RequestTemplate template) {
        String requestKey = StringUtils.trimToNull(properties.getRequestKey());
        if (requestKey == null) {
            log.debug("get requestKey fail");
            return;
        }

        String version = StringUtils.trimToNull(SpringApplicationContextUtil.getContext().getEnvironment()
                .getProperty("spring.cloud.nacos.discovery.metadata.version"));

        if (version == null) {
            log.debug("get metadata version fail");
            return;
        }

        template.header(requestKey, version);
        log.debug("add header[{}: {}]", requestKey, version);
    }
}
