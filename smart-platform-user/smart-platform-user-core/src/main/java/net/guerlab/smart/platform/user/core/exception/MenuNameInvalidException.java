package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 菜单名称无效
 *
 * @author guer
 */
public class MenuNameInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.menuNameInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
