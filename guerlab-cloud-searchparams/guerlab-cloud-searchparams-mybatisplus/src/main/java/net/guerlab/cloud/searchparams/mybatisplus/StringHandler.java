/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

package net.guerlab.cloud.searchparams.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.guerlab.cloud.searchparams.SearchModelType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

/**
 * 字符串类型处理
 *
 * @author guer
 */
public class StringHandler extends AbstractMyBatisPlusSearchParamsHandler {

    /**
     * 通用匹配符
     */
    private static final char PERCENT = '%';

    @Override
    public Class<?> acceptClass() {
        return String.class;
    }

    @Override
    public void setValue(Object object, String fieldName, String columnName, Object value,
            SearchModelType searchModelType, @Nullable String customSql) {
        String str = StringUtils.trimToNull((String) value);

        if (str == null) {
            return;
        }

        QueryWrapper<?> wrapper = (QueryWrapper<?>) object;
        columnName = ColumnNameGetter.getColumnName(columnName, wrapper.getEntityClass());
        switch (searchModelType) {
            case GREATER_THAN -> wrapper.gt(columnName, str);
            case GREATER_THAN_OR_EQUAL_TO -> wrapper.ge(columnName, str);
            case IS_NOT_NULL -> wrapper.isNotNull(columnName);
            case IS_NULL -> wrapper.isNull(columnName);
            case LESS_THAN -> wrapper.lt(columnName, str);
            case LESS_THAN_OR_EQUAL_TO -> wrapper.le(columnName, str);
            case LIKE -> wrapper.like(columnName, str);
            case NOT_LIKE -> wrapper.notLike(columnName, str);
            case START_WITH -> wrapper.likeRight(columnName, str);
            case START_NOT_WITH -> wrapper.notLike(columnName, str + PERCENT);
            case END_WITH -> wrapper.likeLeft(columnName, str);
            case END_NOT_WITH -> wrapper.notLike(columnName, PERCENT + str);
            case NOT_EQUAL_TO -> wrapper.ne(columnName, str);
            case CUSTOM_SQL -> {
                if (customSql == null) {
                    break;
                }
                CustomerSqlInfo info = new CustomerSqlInfo(customSql);
                if (info.batch) {
                    wrapper.apply(info.sql.replaceAll(CustomerSqlInfo.BATCH_REG, "{0}"), value);
                } else if (info.matchFlag) {
                    wrapper.apply(info.sql.replaceAll(CustomerSqlInfo.MATCH_REG, "{0}"), value);
                } else {
                    wrapper.apply(info.sql);
                }
            }
            default -> wrapper.eq(columnName, str);
        }
    }
}
