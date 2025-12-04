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

import java.util.Objects;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import jakarta.annotation.Nullable;

import org.springframework.util.Assert;

/**
 * Boolean Converter.
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class AbstractBooleanConverter implements Converter<Boolean> {

	private final String trueValue;

	private final String falseValue;

	/**
	 * 创建Boolean Converter.
	 *
	 * @param trueValue  true value
	 * @param falseValue false value
	 */
	public AbstractBooleanConverter(String trueValue, String falseValue) {
		Assert.hasText(trueValue, () -> "trueValue is empty");
		Assert.hasText(falseValue, () -> "falseValue is empty");
		Assert.isTrue(!Objects.equals(trueValue, falseValue), () -> "trueValue and falseValue cannot be equals");
		this.trueValue = trueValue;
		this.falseValue = falseValue;
	}

	@Override
	public Class<?> supportJavaTypeKey() {
		return Boolean.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public Boolean convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		return trueValue.equals(cellData.getStringValue());
	}

	@Override
	public WriteCellData<?> convertToExcelData(@Nullable Boolean value, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		String val = "";
		if (value != null) {
			val = value ? trueValue : falseValue;
		}
		return new WriteCellData(val);
	}
}
