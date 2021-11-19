/*
 * Copyright 2018-2021 guerlab.net and other contributors.
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
import net.guerlab.cloud.searchparams.OrderByType;
import net.guerlab.cloud.searchparams.SearchModelType;
import org.springframework.lang.Nullable;

/**
 * 排序类型处理
 *
 * @author guer
 */
public class OrderByHandler extends AbstractMyBatisPlusSearchParamsHandler {

    @Override
    public Class<?> acceptClass() {
        return OrderByType.class;
    }

    @Override
    public void setValue(Object object, String fieldName, String columnName, Object value,
            SearchModelType searchModelType, @Nullable String customSql) {
        QueryWrapper<?> wrapper = (QueryWrapper<?>) object;
        OrderByType type = (OrderByType) value;

        if (type == OrderByType.DESC) {
            wrapper.orderByDesc(columnName);
        } else {
            wrapper.orderByAsc(columnName);
        }
    }
}
