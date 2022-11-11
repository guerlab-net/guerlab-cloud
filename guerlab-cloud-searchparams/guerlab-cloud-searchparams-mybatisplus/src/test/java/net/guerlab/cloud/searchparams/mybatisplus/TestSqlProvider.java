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
	public boolean accept(Class<?> fieldClass, Object object) {
		return Collection.class.isAssignableFrom(fieldClass) && object instanceof QueryWrapper;
	}

	@Override
	public void apply(Object object, Object value) {
		QueryWrapper<?> queryWrapper = (QueryWrapper<?>) object;
		Collection<?> values = (Collection<?>) value;
		queryWrapper.and((sw) -> values.forEach(v -> sw.or().eq("_v", v)));
	}
}
