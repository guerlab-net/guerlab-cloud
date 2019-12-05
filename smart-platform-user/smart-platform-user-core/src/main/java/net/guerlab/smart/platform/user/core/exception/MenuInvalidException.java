package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 菜单无效
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class MenuInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.menuInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
