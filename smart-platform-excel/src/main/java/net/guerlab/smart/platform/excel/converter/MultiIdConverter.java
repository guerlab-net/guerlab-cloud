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
import net.guerlab.smart.platform.commons.domain.MultiId;
import org.apache.commons.lang3.StringUtils;

/**
 * MultiId Converter
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class MultiIdConverter implements Converter<MultiId> {

    public static final MultiIdConverter INSTANCE = new MultiIdConverter();

    @Override
    public Class supportJavaTypeKey() {
        return MultiId.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public MultiId convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        String value = StringUtils.trimToEmpty(cellData.getStringValue());

        MultiId multiString = new MultiId();

        for (String val : value.split(",")) {
            try {
                multiString.add(Long.parseLong(val));
            } catch (Exception ignored) {

            }
        }

        return multiString;
    }

    @Override
    public CellData convertToExcelData(MultiId value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        return new CellData(StringUtils.join(value, ","));
    }
}
