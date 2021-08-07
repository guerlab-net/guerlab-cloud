/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

import java.io.Serial;

/**
 * AccessToken无效异常
 *
 * @author guer
 */
public class AccessTokenInvalidException extends AbstractI18nApplicationException {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.accessTokenInvalid";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }

    @Override
    public int getErrorCode() {
        return 401;
    }
}
