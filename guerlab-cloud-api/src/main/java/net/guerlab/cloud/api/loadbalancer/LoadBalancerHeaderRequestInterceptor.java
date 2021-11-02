/*
 * Copyright 2018-2022 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

        String version = StringUtils.trimToNull(SpringApplicationContextUtil.getContext().getEnvironment().getProperty("spring.cloud.nacos.discovery.metadata.version"));

        if (version == null) {
            log.debug("get metadata version fail");
            return;
        }

        template.header(requestKey, version);
        log.debug("add header[{}: {}]", requestKey, version);
    }
}
