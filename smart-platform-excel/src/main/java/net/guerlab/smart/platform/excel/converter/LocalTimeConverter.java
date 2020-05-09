package net.guerlab.smart.platform.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import net.guerlab.commons.time.Formats;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalTime Converter
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class LocalTimeConverter implements Converter<LocalTime> {

    public static final LocalTimeConverter INSTANCE = new LocalTimeConverter();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Formats.STANDARD_TIME.format);

    @Override
    public Class supportJavaTypeKey() {
        return LocalTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        return LocalTime.parse(cellData.getStringValue(), FORMATTER);
    }

    @Override
    public CellData convertToExcelData(LocalTime value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        return new CellData(value.format(FORMATTER));
    }
}
