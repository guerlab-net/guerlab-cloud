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

package net.guerlab.cloud.core.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.annotation.Nullable;

/**
 * 版本.
 *
 * @author guer
 */
public record Version(long value, @Nullable Version children) implements Comparable<Version> {

	/**
	 * 空版本.
	 */
	public static final Version EMPTY = new Version(0, null);

	private static final String VERSION_REG = "\\d+(\\.\\d+)*";

	/**
	 * 解析版本.
	 *
	 * @param versionString 版本字符串
	 * @return 版本
	 */
	@Nullable
	public static Version parse(@Nullable String versionString) {
		versionString = formatVersion(versionString);
		if (versionString == null) {
			return null;
		}

		String[] versions = versionString.split("\\.");

		Version version = null;
		String v;
		for (int i = versions.length - 1; i >= 0; i--) {
			v = versions[i];
			version = new Version(Long.parseLong(v), version);
		}
		return version;
	}

	/**
	 * 格式化版本字符串.
	 *
	 * @param originVersion 原始版本字符串
	 * @return 格式化后的版本字符串
	 */
	@Nullable
	public static String formatVersion(@Nullable String originVersion) {
		if (originVersion == null) {
			return null;
		}
		Pattern pattern = Pattern.compile(VERSION_REG);
		Matcher matcher = pattern.matcher(originVersion);
		return matcher.find() ? matcher.group() : null;
	}

	/**
	 * 判断版本对象是否为空.
	 *
	 * @param version 版本
	 * @return 是否为空
	 */
	public static boolean isEmpty(@Nullable Version version) {
		return version == null || version == EMPTY;
	}

	/**
	 * 判断版本对象是否非空.
	 *
	 * @param version 版本
	 * @return 是否非空
	 */
	public static boolean notEmpty(@Nullable Version version) {
		return !isEmpty(version);
	}

	/**
	 * 获取安全下级.
	 *
	 * @return 安全下级
	 */
	public Version safeChildren() {
		return children == null ? EMPTY : children;
	}

	@Override
	public int compareTo(Version o) {
		if (!Objects.equals(o.value, value)) {
			return Long.compare(value, o.value);
		}
		else if (children != null && o.children != null) {
			return children.compareTo(o.children);
		}
		else if (children == null && o.children == null) {
			return 0;
		}
		else if (children == null) {
			return -1;
		}
		else {
			return 1;
		}
	}

	@Override
	public String toString() {
		if (children == null) {
			return String.valueOf(value);
		}
		else {
			return value + "." + children;
		}
	}
}
