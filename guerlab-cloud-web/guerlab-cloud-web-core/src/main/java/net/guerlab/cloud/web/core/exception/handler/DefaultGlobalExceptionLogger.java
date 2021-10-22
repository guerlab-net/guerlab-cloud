package net.guerlab.cloud.web.core.exception.handler;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.web.core.properties.GlobalExceptionProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

/**
 * 默认全局异常处理日志记录器
 *
 * @author guer
 */
@Slf4j
public class DefaultGlobalExceptionLogger implements GlobalExceptionLogger {

    private final AntPathMatcher matcher = new AntPathMatcher();

    private final GlobalExceptionProperties properties;

    public DefaultGlobalExceptionLogger(GlobalExceptionProperties properties) {
        this.properties = properties;
    }

    @Override
    public void debug(Throwable e, String requestMethod, String requestPath) {
        for (GlobalExceptionProperties.Url url : properties.getLogIgnorePaths()) {
            String path = StringUtils.trimToNull(url.getPath());
            if (path == null) {
                continue;
            }
            HttpMethod method = url.getMethod();
            if (method != null && method.matches(requestMethod)) {
                continue;
            }
            if (matcher.match(path, requestPath)) {
                continue;
            }

            log.debug(String.format("request uri[%s %s]", requestMethod, requestPath), e);
        }
    }
}
