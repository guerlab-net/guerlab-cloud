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
package net.guerlab.cloud.core.sequence;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 系统时钟
 *
 * @author guer
 */
public class SystemClock {

    private final AtomicLong now;

    private SystemClock() {
        now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    @SuppressWarnings("SameReturnValue")
    private static SystemClock instance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * 获取当前时钟
     *
     * @return 当前时钟
     */
    public static long now() {
        return instance().currentTimeMillis();
    }

    /**
     * 获取当前日期字符串
     *
     * @return 当前日期字符串
     */
    @SuppressWarnings("unused")
    public static String nowDate() {
        return new Timestamp(instance().currentTimeMillis()).toString();
    }

    @SuppressWarnings("AlibabaThreadPoolCreation")
    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "System Clock");
            thread.setDaemon(true);
            return thread;
        });
        scheduler.scheduleAtFixedRate(() -> now.set(System.currentTimeMillis()), 1L, 1L, TimeUnit.MILLISECONDS);
    }

    private long currentTimeMillis() {
        return now.get();
    }

    private static class InstanceHolder {

        public static final SystemClock INSTANCE = new SystemClock();

        private InstanceHolder() {
        }
    }
}
