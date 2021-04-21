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
package net.guerlab.smart.platform.commons.util;

import java.util.Objects;

/**
 * 更新工具类
 *
 * @author guer
 */
public class UpdateUtils {

    private UpdateUtils() {

    }

    /**
     * 获取更新内容，当新旧内容一致时返回null，否则返回新内容
     *
     * @param newData
     *         新内容
     * @param oldData
     *         旧内容
     * @param <T>
     *         内容格式
     * @return 更新内容
     */
    public static <T> T getUpdateValue(T newData, T oldData) {
        return Objects.equals(newData, oldData) ? null : newData;
    }
}
