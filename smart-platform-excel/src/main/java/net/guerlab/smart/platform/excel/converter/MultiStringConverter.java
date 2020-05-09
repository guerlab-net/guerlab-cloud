package net.guerlab.smart.platform.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import net.guerlab.smart.platform.commons.domain.MultiString;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * MultiString Converter
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class MultiStringConverter implements Converter<MultiString> {

    public static final MultiStringConverter INSTANCE = new MultiStringConverter();

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
