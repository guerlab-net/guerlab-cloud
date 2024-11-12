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

package net.guerlab.cloud.commons.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.annotation.Nullable;

/**
 * ID集合.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public class MultiId extends ArrayList<Long> {

	/**
	 * 创建ID集合.
	 */
	public MultiId() {
	}

	/**
	 * 通过指定ID列表创建ID集合.
	 *
	 * @param ids ID列表
	 */
	public MultiId(Collection<Long> ids) {
		super(ids);
	}

	/**
	 * 通过ID列表创建ID集合.
	 *
	 * @param ids ID列表
	 * @return ID集合
	 */
	public static MultiId of(Long... ids) {
		return new MultiId(Arrays.asList(ids));
	}

	/**
	 * 通过ID列表创建ID集合.
	 *
	 * @param ids ID列表
	 * @return ID集合
	 */
	public static MultiId filterNull(@Nullable Collection<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return new MultiId();
		}

		return ids.stream().filter(Objects::nonNull).collect(Collectors.toCollection(MultiId::new));
	}
}
