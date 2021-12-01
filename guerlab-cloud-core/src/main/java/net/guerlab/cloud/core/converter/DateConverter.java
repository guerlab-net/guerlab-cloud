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
package net.guerlab.cloud.core.converter;

import net.guerlab.commons.time.TimeHelper;

import java.util.Date;

/**
 * 日期转换
 *
 * @author guer
 */
public class DateConverter implements AutoLoadConverter<String, Date> {

    @Override
    public Date convert(String source) {
        return TimeHelper.parse(source);
    }

}