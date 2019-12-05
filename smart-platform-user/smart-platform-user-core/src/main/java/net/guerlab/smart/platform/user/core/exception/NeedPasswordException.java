package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 需要密码
 *
 * @author guer
 */
public class NeedPasswordException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.needPassword";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
