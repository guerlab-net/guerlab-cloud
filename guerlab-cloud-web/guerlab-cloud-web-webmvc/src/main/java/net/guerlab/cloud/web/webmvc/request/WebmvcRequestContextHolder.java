package net.guerlab.cloud.web.webmvc.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * webmvc请求上下文保持
 *
 * @author guer
 */
@Slf4j
public class WebmvcRequestContextHolder implements net.guerlab.cloud.web.core.request.RequestContextHolder {

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    private static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    private static String parseRequestUri(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();

        if (contextPath != null) {
            String newRequestUri = requestUri.replaceFirst(contextPath, "");
            log.debug("replace requestUri[form={}, to={}]", requestUri, newRequestUri);
            requestUri = newRequestUri;
        }

        return requestUri;
    }

    @Nullable
    @Override
    public String getRequestMethod() {
        return getRequest().getMethod();
    }

    @Nullable
    @Override
    public String getRequestPath() {
        return parseRequestUri(getRequest());
    }
}
