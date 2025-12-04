/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

import java.lang.reflect.Field;
import java.util.Collection;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import net.guerlab.cloud.searchparams.SqlProvider;

/**
 * TestSqlProvider.
 *
 * @author guer
 */
public class TestSqlProvider implements SqlProvider {

	@Override
	public boolean accept(Object object, Object value, Field field) {
		return object instanceof QueryWrapper && value instanceof Collection;
	}

	@Override
	public void apply(Object object, Object value, Field field) {
		QueryWrapper<?> queryWrapper = (QueryWrapper<?>) object;
		Collection<?> values = (Collection<?>) value;
		queryWrapper.and((sw) -> values.forEach(v -> sw.or().eq("_v", v)));
	}
}
