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

package net.guerlab.cloud.server.mybatis.plus.interceptor;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import net.guerlab.cloud.server.jdbc.PointcutProperties;

/**
 * 匹配结果.
 *
 * @author guer
 */
final class MatchResult implements Comparable<MatchResult> {

	/**
	 * 是否类名匹配.
	 */
	private final boolean classNameMatch;

	/**
	 * 是否方法匹配.
	 */
	private final boolean methodNameMatch;

	/**
	 * 配置项.
	 */
	private final PointcutProperties properties;

	/**
	 * 创建匹配结果.
	 *
	 * @param classNameMatch  是否类名匹配
	 * @param methodNameMatch 是否方法匹配
	 * @param properties      配置项
	 */
	MatchResult(boolean classNameMatch, boolean methodNameMatch,
			PointcutProperties properties) {
		this.classNameMatch = classNameMatch;
		this.methodNameMatch = methodNameMatch;
		this.properties = properties;
	}

	/**
	 * 是否有任意匹配.
	 *
	 * @return 有任意匹配
	 */
	boolean anyMath() {
		return classNameMatch || methodNameMatch;
	}

	private int orderKind() {
		int result = 0;
		if (classNameMatch) {
			result += 10;
		}
		if (methodNameMatch) {
			result += 1;
		}
		return result;
	}

	@Override
	public int compareTo(@NotNull MatchResult o) {
		return o.orderKind() - orderKind();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof MatchResult m)) {
			return false;
		}

		return classNameMatch == m.classNameMatch &&
				methodNameMatch == m.methodNameMatch &&
				Objects.equals(properties, m.properties);
	}

	public boolean classNameMatch() {
		return classNameMatch;
	}

	public boolean methodNameMatch() {
		return methodNameMatch;
	}

	public PointcutProperties properties() {
		return properties;
	}

	@Override
	public int hashCode() {
		return Objects.hash(classNameMatch, methodNameMatch, properties);
	}

	@Override
	public String toString() {
		return "MatchResult[" +
				"classNameMatch=" + classNameMatch + ", " +
				"methodNameMatch=" + methodNameMatch + ", " +
				"properties=" + properties + ']';
	}

}
