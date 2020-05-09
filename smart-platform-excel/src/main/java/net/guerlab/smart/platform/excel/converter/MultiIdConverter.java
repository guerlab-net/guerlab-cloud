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
