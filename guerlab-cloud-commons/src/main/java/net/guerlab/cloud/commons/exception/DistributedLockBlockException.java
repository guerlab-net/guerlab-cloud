/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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
package net.guerlab.cloud.commons.exception;

import net.guerlab.cloud.core.exception.AbstractI18nApplicationException;

import java.io.Serial;

/**
 * 分布式锁阻塞异常
 *
 * @author guer
 */
public class DistributedLockBlockException extends AbstractI18nApplicationException {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_KEY = "message.exception.commons.distributedLockBlock";

    /**
     * 加锁时长
     */
    private final Long time;

    /**
     * 加锁时长单位
     */
    private final String timeUnit;

    /**
     * 构造分布式锁阻塞异常
     *
     * @param time
     *         加锁时长
     * @param timeUnit
     *         加锁时长单位
     */
    public DistributedLockBlockException(Long time, String timeUnit) {
        this.time = time;
        this.timeUnit = timeUnit;
    }

    @Override
    protected String getKey() {
        return MESSAGE_KEY;
    }

    @Override
    public int getErrorCode() {
        return 429;
    }

    @Override
    protected Object[] getArgs() {
        return new Object[] { time, timeUnit };
    }
}
