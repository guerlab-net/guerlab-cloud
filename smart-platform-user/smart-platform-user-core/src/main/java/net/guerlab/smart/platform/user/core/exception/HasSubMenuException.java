package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 拥有下级菜单
 *
 * @author guer
 */
public class HasSubMenuException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.hasSubMenu";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
