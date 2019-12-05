package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 缺少双因子认证信息
 *
 * @author guer
 */
public class OtpInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.otpInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }

    @Override
    public int getErrorCode() {
        return 900;
    }
}
