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

package net.guerlab.commons.number;

import java.util.Arrays;

import jakarta.annotation.Nullable;

/**
 * 数值助手.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class NumberHelper {

	private NumberHelper() {
	}

	/**
	 * 判断是否相等.
	 *
	 * @param a   数值a
	 * @param b   数值b
	 * @param <T> 数值类型
	 * @return 是否相等
	 */
	public static <T extends Comparable<T>> boolean isEquals(@Nullable T a, @Nullable T b) {
		return a != null && b != null && a.compareTo(b) == 0;
	}

	/**
	 * 判断是否大于0.
	 *
	 * @param number 数值
	 * @return 是否大于0
	 */
	public static boolean greaterZero(@Nullable Number number) {
		return number != null && number.doubleValue() > 0;
	}

	/**
	 * 判断是否大于等于0.
	 *
	 * @param number 数值
	 * @return 是否大于等于0
	 */
	public static boolean greaterOrEqualZero(@Nullable Number number) {
		return number != null && number.doubleValue() >= 0;
	}

	/**
	 * 判断是否小于0.
	 *
	 * @param number 数值
	 * @return 是否小于0
	 */
	public static boolean lessZero(@Nullable Number number) {
		return number != null && number.doubleValue() < 0;
	}

	/**
	 * 判断是否小于等于0.
	 *
	 * @param number 数值
	 * @return 是否小于等于0
	 */
	public static boolean lessOrEqualZero(@Nullable Number number) {
		return number != null && number.doubleValue() <= 0;
	}

	/**
	 * 判断是否大于0.
	 *
	 * @param numbers 数值列表
	 * @return 是否大于0
	 */
	public static boolean anyGreaterZero(@Nullable Number... numbers) {
		if (numbers == null || numbers.length == 0) {
			return false;
		}
		return Arrays.stream(numbers).anyMatch(NumberHelper::greaterZero);
	}

	/**
	 * 判断是否大于0.
	 *
	 * @param numbers 数值列表
	 * @return 是否大于0
	 */
	public static boolean allGreaterZero(@Nullable Number... numbers) {
		if (numbers == null || numbers.length == 0) {
			return false;
		}
		return Arrays.stream(numbers).allMatch(NumberHelper::greaterZero);
	}

	/**
	 * 判断是否大于等于0.
	 *
	 * @param numbers 数值列表
	 * @return 是否大于等于0
	 */
	public static boolean anyGreaterOrEqualZero(@Nullable Number... numbers) {
		if (numbers == null || numbers.length == 0) {
			return false;
		}
		return Arrays.stream(numbers).anyMatch(NumberHelper::greaterOrEqualZero);
	}

	/**
	 * 判断是否大于等于0.
	 *
	 * @param numbers 数值列表
	 * @return 是否大于等于0
	 */
	public static boolean allGreaterOrEqualZero(@Nullable Number... numbers) {
		if (numbers == null || numbers.length == 0) {
			return false;
		}
		return Arrays.stream(numbers).allMatch(NumberHelper::greaterOrEqualZero);
	}

	/**
	 * 判断是否小于0.
	 *
	 * @param numbers 数值列表
	 * @return 是否小于0
	 */
	public static boolean anyLessZero(@Nullable Number... numbers) {
		if (numbers == null || numbers.length == 0) {
			return false;
		}
		return Arrays.stream(numbers).anyMatch(NumberHelper::lessZero);
	}

	/**
	 * 判断是否小于0.
	 *
	 * @param numbers 数值列表
	 * @return 是否小于0
	 */
	public static boolean allLessZero(@Nullable Number... numbers) {
		if (numbers == null || numbers.length == 0) {
			return false;
		}
		return Arrays.stream(numbers).allMatch(NumberHelper::lessZero);
	}

	/**
	 * 判断是否小于等于0.
	 *
	 * @param numbers 数值列表
	 * @return 是否小于等于0
	 */
	public static boolean anyLessOrEqualZero(@Nullable Number... numbers) {
		if (numbers == null || numbers.length == 0) {
			return false;
		}
		return Arrays.stream(numbers).anyMatch(NumberHelper::lessOrEqualZero);
	}

	/**
	 * 判断是否小于等于0.
	 *
	 * @param numbers 数值列表
	 * @return 是否小于等于0
	 */
	public static boolean allLessOrEqualZero(@Nullable Number... numbers) {
		if (numbers == null || numbers.length == 0) {
			return false;
		}
		return Arrays.stream(numbers).allMatch(NumberHelper::lessOrEqualZero);
	}

}
