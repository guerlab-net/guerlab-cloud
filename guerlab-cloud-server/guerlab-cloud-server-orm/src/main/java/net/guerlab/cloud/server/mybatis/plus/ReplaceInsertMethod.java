/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.server.mybatis.plus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import net.guerlab.cloud.commons.entity.BaseEntity;
import net.guerlab.commons.reflection.FieldUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * replace模式插入
 *
 * @author guer
 */
public class ReplaceInsertMethod extends AbstractMethod {

    private static final Collection<Field> BASE_ENTITY_FIELDS = FieldUtil.getFields(BaseEntity.class);

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String sql = "<script>REPLACE INTO %s %s VALUES %s</script>";
        final String fieldSql = prepareFieldSql(tableInfo);
        final String valueSql = prepareValuesSqlForMysqlBatch(tableInfo);
        final String sqlResult = String.format(sql, tableInfo.getTableName(), fieldSql, valueSql);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        return this
                .addInsertMappedStatement(mapperClass, modelClass, "replaceInsertList", sqlSource, new NoKeyGenerator(),
                        null, null);
    }

    private String prepareFieldSql(TableInfo tableInfo) {
        String keyColumn = StringUtils.trimToNull(tableInfo.getKeyColumn());
        StringBuilder fieldSql = new StringBuilder();
        if (keyColumn != null) {
            fieldSql.append(tableInfo.getKeyColumn()).append(",");
        }
        tableInfo.getFieldList().stream().filter(x -> filedFilter(tableInfo, x))
                .forEach(x -> fieldSql.append(x.getColumn()).append(","));
        fieldSql.delete(fieldSql.length() - 1, fieldSql.length());
        fieldSql.insert(0, "(");
        fieldSql.append(")");
        return fieldSql.toString();
    }

    private String prepareValuesSqlForMysqlBatch(TableInfo tableInfo) {
        String keyColumn = StringUtils.trimToNull(tableInfo.getKeyColumn());
        final StringBuilder valueSql = new StringBuilder();
        valueSql.append(
                "<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\"),(\" close=\")\">");
        if (keyColumn != null) {
            valueSql.append("#{item.").append(tableInfo.getKeyProperty()).append("},");
        }
        tableInfo.getFieldList().stream().filter(x -> filedFilter(tableInfo, x))
                .forEach(x -> valueSql.append("#{item.").append(x.getProperty()).append("},"));
        valueSql.delete(valueSql.length() - 1, valueSql.length());
        valueSql.append("</foreach>");
        return valueSql.toString();
    }

    private boolean filedFilter(TableInfo tableInfo, TableFieldInfo fieldInfo) {
        if (BaseEntity.class.isAssignableFrom(tableInfo.getEntityType())) {
            return !BASE_ENTITY_FIELDS.contains(fieldInfo.getField());
        }
        return true;
    }
}
