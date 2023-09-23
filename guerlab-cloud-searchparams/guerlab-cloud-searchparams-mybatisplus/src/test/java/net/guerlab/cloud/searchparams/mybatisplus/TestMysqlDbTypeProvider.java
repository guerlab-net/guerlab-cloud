/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

/**
 * @author guer
 */
public class TestMysqlDbTypeProvider implements DbTypeProvider {
	@Nullable
	@Override
	public DbType getDbType(Object object) {
		return DbType.MYSQL;
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
