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
import net.guerlab.cloud.searchparams.SearchParamsHandler;
import org.springframework.lang.Nullable;

/**
 * 默认处理
 *
 * @author guer
 */
public class DefaultHandler implements SearchParamsHandler {

    @Override
    public void setValue(Object object, String fieldName, String columnName, Object value,
            SearchModelType searchModelType, @Nullable String customSql) {
        QueryWrapper<?> wrapper = (QueryWrapper<?>) object;
        columnName = ColumnNameGetter.getColumnName(columnName, wrapper.getEntityClass());
        switch (searchModelType) {
            case GREATER_THAN:
                wrapper.gt(columnName, value);
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                wrapper.ge(columnName, value);
                break;
            case IS_NOT_NULL:
                wrapper.isNotNull(columnName);
                break;
            case IS_NULL:
                wrapper.isNull(columnName);
                break;
            case LESS_THAN:
                wrapper.lt(columnName, value);
                break;
            case LESS_THAN_OR_EQUAL_TO:
                wrapper.le(columnName, value);
                break;
            case NOT_EQUAL_TO:
            case NOT_LIKE:
            case START_NOT_WITH:
            case END_NOT_WITH:
                wrapper.ne(columnName, value);
                break;
            case CUSTOM_SQL:
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
                break;
            default:
                wrapper.eq(columnName, value);
        }
    }
}
