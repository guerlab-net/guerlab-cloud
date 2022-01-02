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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.guerlab.cloud.searchparams.OrderBy;
import net.guerlab.cloud.searchparams.OrderBys;
import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.commons.reflection.FieldUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排序类型处理
 *
 * @author guer
 */
public class OrderBysHandler extends AbstractMyBatisPlusSearchParamsHandler {

    @Override
    public Class<?> acceptClass() {
        return OrderBys.class;
    }

    @Override
    public void setValue(Object object, String fieldName, String columnName, Object value,
            SearchModelType searchModelType, @Nullable String customSql) {
        QueryWrapper<?> wrapper = (QueryWrapper<?>) object;
        List<OrderBy> orderBys = (OrderBys) value;
        orderBys = orderBys.stream().filter(this::orderByFilter).collect(Collectors.toList());

        Class<?> entityClass = wrapper.getEntityClass();

        for (OrderBy orderBy : orderBys) {
            wrapper.orderBy(true, orderBy.isAsc(), getColumnName(orderBy, entityClass));
        }
    }

    /**
     * 获取字段名
     *
     * @param orderBy
     *         派度字段
     * @param entityClass
     *         实体类类型
     * @return 字段名
     */
    private String getColumnName(OrderBy orderBy, @Nullable Class<?> entityClass) {
        String columnName = orderBy.getColumnName();
        if (entityClass == null) {
            return columnName;
        }

        Field field = FieldUtil.getField(entityClass, columnName);
        if (field == null) {
            return columnName;
        }

        TableField tableField = field.getAnnotation(TableField.class);
        if (tableField != null) {
            String tableFieldValue = StringUtils.trimToNull(tableField.value());
            if (tableFieldValue != null) {
                return tableFieldValue;
            }
        }

        TableId tableId = field.getAnnotation(TableId.class);
        if (tableId != null) {
            String tableIdValue = StringUtils.trimToNull(tableId.value());
            if (tableIdValue != null) {
                return tableIdValue;
            }
        }

        return columnName;
    }

    /**
     * 排序字段过滤
     *
     * @param orderBy
     *         排序字段
     * @return 排序字段是否可以
     */
    private boolean orderByFilter(@Nullable OrderBy orderBy) {
        if (orderBy == null) {
            return false;
        }

        String columnName = StringUtils.trimToNull(orderBy.getColumnName());
        if (columnName == null) {
            return false;
        }

        orderBy.setColumnName(columnName);
        return true;
    }
}
