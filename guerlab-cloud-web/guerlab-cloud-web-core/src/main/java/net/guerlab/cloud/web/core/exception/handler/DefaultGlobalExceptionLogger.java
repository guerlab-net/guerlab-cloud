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
