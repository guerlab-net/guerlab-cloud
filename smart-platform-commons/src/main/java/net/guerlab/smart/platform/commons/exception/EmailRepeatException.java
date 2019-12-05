package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 电子邮件地址重复
 *
 * @author guer
 */
public class EmailRepeatException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.emailRepeat";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
