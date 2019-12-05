package net.guerlab.smart.platform.commons.exception;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 权限错误
 *
 * @author guer
 */
public class PermissionsErrorException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.permissionsError";

    private static final String MESSAGE_KEY_WITH_KEYS = "message.exception.commons.permissionsErrorWithKeys";

    private Collection<String> keys;

    private boolean hasKeys;

    /**
     * 权限错误
     */
    public PermissionsErrorException() {
    }

    /**
     * 权限错误
     *
     * @param keys
     *         权限关键字列表
     */
    public PermissionsErrorException(Collection<String> keys) {
        this.keys = keys;

        hasKeys = !CollectionUtil.isBlank(keys);
    }

    @Override
    protected String getKey() {
        return hasKeys ? MESSAGE_KEY_WITH_KEYS : MESSAGE_KEY;
    }

    @Override
    protected Object[] getArgs() {
        return hasKeys ? new Object[] { StringUtils.join(keys.toArray(), ",") } : new Object[0];
    }
}
