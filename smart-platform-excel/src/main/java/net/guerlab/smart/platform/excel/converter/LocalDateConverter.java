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
package net.guerlab.smart.platform.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import net.guerlab.commons.time.Formats;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate Converter
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class LocalDateConverter implements Converter<LocalDate> {

    public static final LocalDateConverter INSTANCE = new LocalDateConverter();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Formats.SIMPLE_DATE.format);

    @Override
    public Class supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        return LocalDate.parse(cellData.getStringValue(), FORMATTER);
    }

    @Override
    public CellData convertToExcelData(LocalDate value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        return new CellData(value.format(FORMATTER));
    }
}
