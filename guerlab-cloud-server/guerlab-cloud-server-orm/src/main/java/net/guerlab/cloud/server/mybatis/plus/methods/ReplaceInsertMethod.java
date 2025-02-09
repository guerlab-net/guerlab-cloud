/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

package net.guerlab.cloud.server.mybatis.plus.methods;

import com.baomidou.mybatisplus.core.metadata.TableInfo;

import net.guerlab.cloud.commons.entity.IBaseEntity;

/**
 * replace模式插入.
 *
 * @param <E> 基础类类型
 * @author guer
 */
public class ReplaceInsertMethod<E extends IBaseEntity> extends AbstractMysqlBatchInsertMethod<E> {

	/**
	 * 构造replace模式插入方法.
	 *
	 * @param baseClass 基础类
	 */
	public ReplaceInsertMethod(Class<E> baseClass) {
		super(baseClass, "replaceInsertList");
	}

	@Override
	protected String buildSqlResult(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		String sql = "<script>REPLACE INTO %s %s VALUES %s</script>";
		String fieldSql = prepareFieldSql(tableInfo);
		String valueSql = prepareValuesSqlForMysqlBatch(tableInfo);
		return String.format(sql, tableInfo.getTableName(), fieldSql, valueSql);
	}
}
