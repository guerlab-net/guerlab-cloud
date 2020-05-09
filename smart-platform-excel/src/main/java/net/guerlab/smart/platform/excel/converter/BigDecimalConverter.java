package net.guerlab.smart.platform.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;

/**
 * BigDecimal Converter
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class BigDecimalConverter implements Converter<BigDecimal> {

    public static final BigDecimalConverter INSTANCE = new BigDecimalConverter();

    @Override
    public Class supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public BigDecimal convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        return new BigDecimal(cellData.getStringValue());
    }

    @Override
    public CellData convertToExcelData(BigDecimal value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        return new CellData(value.toString());
    }
}
