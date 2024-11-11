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
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import net.guerlab.cloud.commons.entity.IBaseEntity;

/**
 * 抽象批量插入.
 *
 * @param <E> 基础类类型
 * @author guer
 */
public abstract class AbstractMysqlBatchInsertMethod<E extends IBaseEntity> extends AbstractAutoLoadMethod<E> {

	/**
	 * 构造抽象批量插入方法.
	 *
	 * @param baseClass  基础类
	 * @param methodName 方法名
	 */
	protected AbstractMysqlBatchInsertMethod(Class<E> baseClass, String methodName) {
		super(baseClass, methodName);
	}

	/**
	 * 准备字段sql.
	 *
	 * @param tableInfo 表信息
	 * @return 字段sql
	 */
	protected String prepareFieldSql(TableInfo tableInfo) {
		String keyColumn = StringUtils.trimToNull(tableInfo.getKeyColumn());
		StringBuilder fieldSql = new StringBuilder();
		if (keyColumn != null) {
			fieldSql.append(keyColumn).append(",");
		}
		getFieldStream(tableInfo).forEach(x -> fieldSql.append(x.getColumn()).append(","));
		fieldSql.delete(fieldSql.length() - 1, fieldSql.length());
		fieldSql.insert(0, "(");
		fieldSql.append(")");
		return fieldSql.toString();
	}

	/**
	 * 准备值列表字段sql.
	 *
	 * @param tableInfo 表信息
	 * @return 值列表字段sql
	 */
	protected String prepareValuesSqlForMysqlBatch(TableInfo tableInfo) {
		String keyProperty = StringUtils.trimToNull(tableInfo.getKeyProperty());
		StringBuilder valueSql = new StringBuilder();
		valueSql.append(
				"<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\"),(\" close=\")\">");
		if (keyProperty != null) {
			valueSql.append("#{item.").append(keyProperty).append("},");
		}
		getFieldStream(tableInfo).forEach(x -> valueSql.append("#{item.").append(x.getProperty()).append("},"));
		valueSql.delete(valueSql.length() - 1, valueSql.length());
		valueSql.append("</foreach>");
		return valueSql.toString();
	}

	@Override
	public final MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		String sqlResult = buildSqlResult(mapperClass, modelClass, tableInfo);
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
		return addInsertMappedStatement(mapperClass, modelClass, methodName, sqlSource, new NoKeyGenerator(), null,
				null);
	}

	/**
	 * 构造sql.
	 *
	 * @param mapperClass mapper类型
	 * @param modelClass  model类型
	 * @param tableInfo   表信息
	 * @return sql
	 */
	protected abstract String buildSqlResult(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo);
}
