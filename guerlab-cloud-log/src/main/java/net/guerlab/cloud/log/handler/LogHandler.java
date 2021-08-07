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
package net.guerlab.cloud.log.handler;

import org.springframework.lang.Nullable;

/**
 * 日志处理接口
 *
 * @author guer
 */
@FunctionalInterface
public interface LogHandler {

    /**
     * 日志处理
     *
     * @param logContent
     *         日志内容
     * @param requestMethod
     *         请求方法
     * @param requestUri
     *         请求uri
     * @param operationParam
     *         操作参数
     * @param result
     *         响应
     * @param ex
     *         异常
     */
    void handler(String logContent, String requestMethod, String requestUri, @Nullable Object operationParam,
            @Nullable Object result, @Nullable Throwable ex);
}
