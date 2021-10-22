package net.guerlab.cloud.auth.webmvc.test;

import net.guerlab.cloud.auth.webmvc.interceptor.AbstractHandlerInterceptor;
import net.guerlab.cloud.commons.exception.AccessTokenInvalidException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author guer
 */
public class TestTokenHandlerAfterInterceptor extends AbstractHandlerInterceptor {

    @Override
    protected void preHandleWithoutToken() {
        if (StringUtils.isBlank(TestContentHandler.getName())) {
            throw new AccessTokenInvalidException();
        }
    }
}
