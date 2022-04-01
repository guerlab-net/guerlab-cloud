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

/**
 * 自定义sql信息.
 *
 * @author guer
 */
class CustomerSqlInfo {

	public static final String MATCH_FLAG = "?";

	public static final String BATCH_FLAG = "?*";

	public static final String MATCH_REG = "\\?";

	public static final String BATCH_REG = "\\?\\*";

	/**
	 * sql片段.
	 */
	final String sql;

	/**
	 * 是否包含匹配符.
	 */
	final boolean matchFlag;

	/**
	 * 批量模式.
	 */
	final boolean batch;

	/**
	 * 通过sql片段构造自定义sql信息.
	 *
	 * @param sql 自定义sql信息
	 */
	CustomerSqlInfo(String sql) {
		this.sql = "(" + sql + ")";
		matchFlag = sql.contains(MATCH_FLAG);
		batch = matchFlag && sql.contains(BATCH_FLAG);
	}
}
