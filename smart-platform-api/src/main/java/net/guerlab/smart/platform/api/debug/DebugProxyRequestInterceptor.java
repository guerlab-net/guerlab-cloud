/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.smart.platform.api.debug;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.smart.platform.api.properties.DebugProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

/**
 * 开发代理请求拦截器
 *
 * @author guer
 */
@Slf4j
public class DebugProxyRequestInterceptor implements RequestInterceptor {

    private static final String SCHEME_HTTP = "http://";

    private static final String SCHEME_HTTPS = "https://";

    private static final String PATH_SEPARATOR = "/";

    private final DebugProperties properties;

    public DebugProxyRequestInterceptor(DebugProperties properties) {
        this.properties = properties;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (!properties.isEnable()) {
            return;
        }

        String serviceName = requestTemplate.feignTarget().name();

        if (serviceName.contains(PATH_SEPARATOR)) {
            serviceName = serviceName.split(PATH_SEPARATOR)[0];
        }
        if (isLocalService(serviceName)) {
            return;
        }

        String targetUrl = requestTemplate.feignTarget().url();
        String url;

        if (targetUrl.startsWith(SCHEME_HTTPS)) {
            url = targetUrl.replace(SCHEME_HTTPS, properties.getGatewayProxyUrl());
        } else {
            url = targetUrl.replace(SCHEME_HTTP, properties.getGatewayProxyUrl());
        }

        requestTemplate.target(url);
        requestTemplate.header(properties.getProxyHeaderName(), properties.getProxyHeaderValue());

        log.debug("proxy {} to {}", targetUrl, url);
    }

    private boolean isLocalService(String serviceName) {
        if (CollectionUtils.isEmpty(properties.getLocalServices())) {
            return false;
        }

        return properties.getLocalServices().stream().map(StringUtils::trimToNull).filter(Objects::nonNull)
                .anyMatch(service -> service.equalsIgnoreCase(serviceName));
    }
}
