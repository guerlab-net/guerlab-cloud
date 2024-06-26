/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

import jakarta.annotation.Nullable;

import org.springframework.core.Ordered;

/**
 * @author guer
 */
public class TestOracleDbTypeProvider implements DbTypeProvider {
	@Nullable
	@Override
	public DbType getDbType(Object object) {
		return DbType.ORACLE;
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
