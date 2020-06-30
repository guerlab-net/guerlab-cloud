package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 未发现可使用的token工厂
 *
 * @author guer
 */
public class NotFoundCanUseTokenFactoryException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.notFoundCanUseTokenFactory";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }

    @Override
    public int getErrorCode() {
        return 401;
    }
}
