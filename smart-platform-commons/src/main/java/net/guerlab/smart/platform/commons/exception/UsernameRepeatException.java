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
package net.guerlab.smart.platform.commons.exception;

import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;

/**
 * 用户名重复
 *
 * @author guer
 */
public class UsernameRepeatException extends AbstractI18nApplicationException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.usernameRepeat";

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }
}
