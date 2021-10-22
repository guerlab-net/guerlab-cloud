package net.guerlab.cloud.auth.webflux.test;

import net.guerlab.cloud.auth.webflux.filter.AbstractHandlerFilter;
import net.guerlab.cloud.commons.exception.AccessTokenInvalidException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author guer
 */
public class TestTokenAfterHandlerFilter extends AbstractHandlerFilter {

    @Override
    protected void preHandleWithoutToken() {
        if (StringUtils.isBlank(TestContentHandler.getName())) {
            throw new AccessTokenInvalidException();
        }
    }
}
