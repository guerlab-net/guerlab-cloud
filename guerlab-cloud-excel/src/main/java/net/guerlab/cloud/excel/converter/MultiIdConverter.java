/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.lang3.StringUtils;

import net.guerlab.cloud.commons.domain.MultiId;

/**
 * MultiId Converter.
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class MultiIdConverter implements Converter<MultiId> {

	private static final String SEPARATOR = ",";

	@Override
	public Class<?> supportJavaTypeKey() {
		return MultiId.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public MultiId convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		String value = StringUtils.trimToEmpty(cellData.getStringValue());

		MultiId multiString = new MultiId();

		for (String val : value.split(SEPARATOR)) {
			try {
				multiString.add(Long.parseLong(val));
			}
			catch (Exception ignored) {
				// ignore this exception
			}
		}

		return multiString;
	}

	@Override
	public WriteCellData<?> convertToExcelData(MultiId value, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		return new WriteCellData(StringUtils.join(value, SEPARATOR));
	}
}
