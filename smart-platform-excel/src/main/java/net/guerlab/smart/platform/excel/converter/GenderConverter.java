package net.guerlab.smart.platform.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import net.guerlab.smart.platform.commons.enums.Gender;
import org.apache.commons.lang3.StringUtils;

/**
 * Gender Converter
 *
 * @author guer
 */
@SuppressWarnings("rawtypes")
public class GenderConverter implements Converter<Gender> {

    public static final GenderConverter INSTANCE = new GenderConverter();

    private static final String MAN = "男";

    private static final String WOMAN = "女";

    private static final String OTHER = "其他";

    @Override
    public Class supportJavaTypeKey() {
        return Gender.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Gender convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        String value = StringUtils.trimToEmpty(cellData.getStringValue());

        switch (value) {
            case MAN:
                return Gender.MAN;
            case WOMAN:
                return Gender.WOMAN;
            default:
                return Gender.OTHER;
        }

    }

    @Override
    public CellData convertToExcelData(Gender value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new CellData(OTHER);
        }

        switch (value) {
            case MAN:
                return new CellData(MAN);
            case WOMAN:
                return new CellData(WOMAN);
            default:
                return new CellData(OTHER);
        }
    }
}
