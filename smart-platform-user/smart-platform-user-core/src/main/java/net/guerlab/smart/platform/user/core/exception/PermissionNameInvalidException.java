package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 权限名称无效
 *
 * @author guer
 */
public class PermissionNameInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.permissionNameInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
