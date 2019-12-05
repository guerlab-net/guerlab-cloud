package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 需要完善信息异常
 *
 * @author guer
 */
public class NeedPerfectInformationException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.needPerfectInformation";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }

    @Override
    public int getErrorCode() {
        return 1001;
    }
}
