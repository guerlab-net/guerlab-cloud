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
package net.guerlab.cloud.server.mybatis.plus.methods;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

/**
 * 插入或更新
 *
 * @author guer
 */
public class InsertOrUpdateMethod extends AbstractMysqlBatchInsertMethod {

    @Override
    protected String methodName() {
        return "insertOrUpdateList";
    }

    @Override
    protected String buildSqlResult(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "<script>INSERT INTO %s %s VALUES %s ON DUPLICATE KEY UPDATE %s</script>";
        String fieldSql = prepareFieldSql(tableInfo);
        String valueSql = prepareValuesSqlForMysqlBatch(tableInfo);
        String updateSql = prepareUpdateSql(tableInfo);
        return String.format(sql, tableInfo.getTableName(), fieldSql, valueSql, updateSql);
    }

    @Nullable
    private String prepareUpdateSql(TableInfo tableInfo) {
        String keyColumn = StringUtils.trimToNull(tableInfo.getKeyColumn());
        StringBuilder updateSql = new StringBuilder();
        if (keyColumn != null) {
            updateSql.append(keyColumn).append(" = ").append(keyColumn).append(",");
        }
        getFieldStream(tableInfo).forEach(tableField -> addUpdateSql(updateSql, tableField));
        if (updateSql.length() > 1) {
            return updateSql.substring(0, updateSql.length() - 1);
        } else {
            return null;
        }
    }

    private void addUpdateSql(StringBuilder updateSql, TableFieldInfo fieldInfo) {
        String fieldName = StringUtils.trimToNull(fieldInfo.getColumn());
        FieldStrategy updateStrategy = fieldInfo.getUpdateStrategy();
        if (updateStrategy != FieldStrategy.NEVER) {
            updateSql.append(fieldName).append(" = VALUES(").append(fieldName).append(")").append(",");
        }
    }
}
