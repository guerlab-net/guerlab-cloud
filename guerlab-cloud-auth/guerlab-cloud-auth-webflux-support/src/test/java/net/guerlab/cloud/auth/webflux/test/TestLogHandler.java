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
package net.guerlab.cloud.auth.webflux.test;

import net.guerlab.cloud.log.annotation.Log;
import net.guerlab.cloud.log.handler.LogHandler;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.lang.Nullable;

/**
 * @author guer
 */
public class TestLogHandler implements LogHandler {

    @Override
    public boolean accept(MethodSignature methodSignature, Log log) {
        return true;
    }

    @Override
    public void handler(String logContent, String requestMethod, String requestUri, @Nullable Object operationParam, @Nullable Object result, @Nullable Throwable ex) {

    }
}
