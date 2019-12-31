package net.guerlab.smart.platform.user.core.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 主职务不能删除
 *
 * @author guer
 */
public class MainDutyCannotDeleteException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.user.mainDutyCannotDelete";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
