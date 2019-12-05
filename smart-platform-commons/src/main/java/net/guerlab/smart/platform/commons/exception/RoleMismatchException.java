package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 角色不匹配异常
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class RoleMismatchException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.roleMismatch";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
