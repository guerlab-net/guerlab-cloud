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

package net.guerlab.cloud.commons.util;

import net.guerlab.cloud.commons.entity.EnableTimeEntity;
import net.guerlab.cloud.commons.exception.StartTimeIsAfterEndTimeException;

import java.time.LocalDateTime;

import static net.guerlab.cloud.commons.Constants.MAX_DATETIME;

/**
 * 启用时间实体工具类
 *
 * @author guer
 */
@SuppressWarnings("unused")
public class EnableTimeEntityUtils {

    private EnableTimeEntityUtils() {

    }

    /**
     * 值处理
     *
     * @param entity
     *         启用时间实体
     */
    public static void valueHandler(EnableTimeEntity entity) {
        LocalDateTime enableStartTime = entity.getEnableStartTime();
        LocalDateTime enableEndTime = entity.getEnableEndTime();
        if (enableStartTime == null) {
            enableStartTime = LocalDateTime.now();
        }
        if (enableEndTime == null || enableEndTime.isAfter(MAX_DATETIME)) {
            enableEndTime = MAX_DATETIME;
        }
        if (enableStartTime.isAfter(enableEndTime)) {
            throw new StartTimeIsAfterEndTimeException();
        }
        entity.setEnableStartTime(enableStartTime);
        entity.setEnableEndTime(enableEndTime);
    }
}
