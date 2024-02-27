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

package net.guerlab.cloud.commons.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.lang.Nullable;

/**
 * String集合.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public class MultiString extends ArrayList<String> {


	/**
	 * 创建String集合.
	 */
	public MultiString() {
	}

	/**
	 * 通过指定字符串列表创建String集合.
	 *
	 * @param stringList 字符串列表
	 */
	public MultiString(Collection<String> stringList) {
		super(stringList);
	}

	/**
	 * 通过字符串列表创建String集合.
	 *
	 * @param stringList 字符串列表
	 * @return String集合
	 */
	public static MultiString of(String... stringList) {
		return new MultiString(Arrays.asList(stringList));
	}

	/**
	 * 通过字符串列表创建String集合.
	 *
	 * @param stringList 字符串列表
	 * @return String集合
	 */
	public static MultiString filterNull(@Nullable Collection<String> stringList) {
		if (stringList == null || stringList.isEmpty()) {
			return new MultiString();
		}

		return stringList.stream().filter(Objects::nonNull).collect(Collectors.toCollection(MultiString::new));
	}
}
