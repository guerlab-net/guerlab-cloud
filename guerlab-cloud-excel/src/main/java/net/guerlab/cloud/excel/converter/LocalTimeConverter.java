/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import net.guerlab.commons.time.TimeHelper;

/**
 * LocalTime Converter.
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class LocalTimeConverter implements Converter<LocalTime> {

	@Override
	public Class<?> supportJavaTypeKey() {
		return LocalTime.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public LocalTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		return TimeHelper.parseLocalTime(cellData.getStringValue());
	}

	@Override
	public WriteCellData<?> convertToExcelData(LocalTime value, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		return new WriteCellData(TimeHelper.format(value, DateTimeFormatter.ISO_LOCAL_TIME));
	}
}
