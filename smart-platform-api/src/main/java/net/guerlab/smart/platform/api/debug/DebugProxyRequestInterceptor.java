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
        String url = targetUrl.replace("http://", properties.getGatewayProxyUrl());
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
