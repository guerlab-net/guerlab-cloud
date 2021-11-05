/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.guerlab.cloud.rsa;

/**
 * RSA异常
 *
 * @author guer
 */
public class RsaException extends RuntimeException {

    public RsaException() {
    }

    public RsaException(String message) {
        super(message);
    }

    public RsaException(String message, Throwable cause) {
        super(message, cause);
    }

    public RsaException(Throwable cause) {
        super(cause);
    }

    public RsaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
