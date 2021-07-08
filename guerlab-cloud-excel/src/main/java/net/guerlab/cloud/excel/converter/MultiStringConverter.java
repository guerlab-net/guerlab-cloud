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
package net.guerlab.cloud.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import net.guerlab.cloud.commons.domain.MultiString;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * MultiString Converter
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class MultiStringConverter implements Converter<MultiString> {

    @Override
    public Class supportJavaTypeKey() {
        return MultiString.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public MultiString convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        String value = StringUtils.trimToEmpty(cellData.getStringValue());

        MultiString multiString = new MultiString();
        multiString.addAll(Arrays.asList(value.split(",")));

        return multiString;
    }

    @Override
    public CellData convertToExcelData(MultiString value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        return new CellData(StringUtils.join(value, ","));
    }
}
