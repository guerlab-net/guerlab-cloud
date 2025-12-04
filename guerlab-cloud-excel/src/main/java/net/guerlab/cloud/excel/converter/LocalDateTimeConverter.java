/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

package net.guerlab.cloud.excel.converter;

import java.time.LocalDateTime;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import net.guerlab.commons.time.TimeHelper;

/**
 * LocalDateTime Converter.
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

	@Override
	public Class<?> supportJavaTypeKey() {
		return LocalDateTime.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public LocalDateTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		return TimeHelper.parseLocalDateTime(cellData.getStringValue());
	}

	@Override
	public WriteCellData<?> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		return new WriteCellData(TimeHelper.format(value));
	}
}
