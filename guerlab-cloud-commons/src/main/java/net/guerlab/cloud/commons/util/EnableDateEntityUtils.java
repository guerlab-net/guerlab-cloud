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

import net.guerlab.cloud.commons.entity.EnableDateEntity;
import net.guerlab.cloud.commons.exception.StartDateIsAfterEndDateException;

import java.time.LocalDate;

import static net.guerlab.cloud.commons.Constants.MAX_DATE;

/**
 * 启用日期实体工具类
 *
 * @author guer
 */
@SuppressWarnings("unused")
public class EnableDateEntityUtils {

    private EnableDateEntityUtils() {

    }

    /**
     * 值处理
     *
     * @param entity
     *         启用日期实体
     */
    public static void valueHandler(EnableDateEntity entity) {
        LocalDate enableStartDate = entity.getEnableStartDate();
        LocalDate enableEndDate = entity.getEnableEndDate();
        if (enableStartDate == null) {
            enableStartDate = LocalDate.now();
        }
        if (enableEndDate == null || enableEndDate.isAfter(MAX_DATE)) {
            enableEndDate = MAX_DATE;
        }
        if (enableStartDate.isAfter(enableEndDate)) {
            throw new StartDateIsAfterEndDateException();
        }
        entity.setEnableStartDate(enableStartDate);
        entity.setEnableEndDate(enableEndDate);
    }
}
