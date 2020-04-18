package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 不支持的版本字段类型
 *
 * @author guer
 */
public class UnsupportedVersionFieldTypeException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.unsupportedVersionFieldType";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}

