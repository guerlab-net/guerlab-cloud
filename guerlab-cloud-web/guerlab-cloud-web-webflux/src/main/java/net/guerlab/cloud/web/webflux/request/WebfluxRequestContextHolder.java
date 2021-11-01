package net.guerlab.cloud.web.webflux.request;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ContextAttributesHolder;
import net.guerlab.cloud.web.core.request.RequestContextHolder;
import net.guerlab.cloud.web.webflux.utils.RequestUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;

/**
 * webflux请求上下文保持
 * Mono
 *
 * @author guer
 */
@Slf4j
public class WebfluxRequestContextHolder implements RequestContextHolder {

    public static ServerHttpRequest getRequest() {
        ContextAttributes contextAttributes = ContextAttributesHolder.get();
        ServerHttpRequest request = (ServerHttpRequest) contextAttributes.get(ContextAttributes.REQUEST_KEY);

        if (request == null) {
            throw new NullPointerException("ServerHttpRequest is null");
        }

        return request;
    }

    @Nullable
    @Override
    public String getRequestMethod() {
        return getRequest().getMethodValue();
    }

    @Nullable
    @Override
    public String getRequestPath() {
        return RequestUtils.parseRequestUri(getRequest());
    }
}
