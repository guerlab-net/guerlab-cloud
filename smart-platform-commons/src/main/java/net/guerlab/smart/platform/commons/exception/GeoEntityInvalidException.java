package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 无效的地理信息实体
 *
 * @author guer
 */
public class GeoEntityInvalidException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.geoEntityInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
