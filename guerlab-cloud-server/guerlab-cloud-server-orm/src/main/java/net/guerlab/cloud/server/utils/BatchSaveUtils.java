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

package net.guerlab.cloud.server.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.guerlab.commons.collection.CollectionUtil;

/**
 * 批量保存工具.
 *
 * @author guer
 */
public final class BatchSaveUtils {

	private BatchSaveUtils() {

	}

	/**
	 * 批量保存过滤.
	 *
	 * @param collection 待保存集合
	 * @param filter     批量保存过滤器
	 * @param <T>        实体类型
	 * @return 过滤后可保存集合
	 */
	public static <T> List<T> filter(Collection<T> collection, Function<? super T, ? extends T> filter) {
		if (CollectionUtil.isEmpty(collection)) {
			return Collections.emptyList();
		}

		List<T> list = collection.stream().filter(Objects::nonNull).map(filter).filter(Objects::nonNull)
				.collect(Collectors.toList());

		return CollectionUtil.isEmpty(list) ? Collections.emptyList() : list;
	}
}
