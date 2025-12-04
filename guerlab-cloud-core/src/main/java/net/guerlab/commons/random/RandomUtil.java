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

package net.guerlab.commons.random;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.annotation.Nullable;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 随机类.
 *
 * @author Guer
 */
@SuppressWarnings("unused")
public final class RandomUtil {

	/**
	 * 默认随机字符集合.
	 */
	public static final char[] CHAR_LIST = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9'};

	private RandomUtil() {
	}

	/**
	 * 随机字符串,取值范围(a-zA-Z0-9).
	 *
	 * @param length 字符串长度
	 * @return 随机字符串
	 */
	public static String nextString(final int length) {
		return nextString(length, CHAR_LIST);
	}

	/**
	 * 自定义取值范围的随机字符串.
	 *
	 * @param length 字符串长度
	 * @param chars  取值范围
	 * @return 随机字符串
	 */
	public static String nextString(final int length, @Nullable final char[] chars) {
		if (length <= 0) {
			return "";
		}

		char[] nowChars = chars;

		if (nowChars == null || nowChars.length == 0) {
			nowChars = CHAR_LIST;
		}

		char[] list = new char[length];

		ThreadLocalRandom random = current();

		for (int i = 0; i < list.length; i++) {
			list[i] = nowChars[random.nextInt(nowChars.length)];
		}

		return new String(list);
	}

	/**
	 * 获取一个随机整型.
	 *
	 * @return 随机整型
	 */
	public static int nextInt() {
		return current().nextInt();
	}

	/**
	 * 获取一个不大于n的随机整型.
	 *
	 * @param n 随机数最大值
	 * @return 不大于n的随机整型
	 */
	public static int nextInt(final int n) {
		return current().nextInt(n);
	}

	/**
	 * 返回一个介于origin和bound之间的整型.
	 *
	 * @param origin origin
	 * @param bound  bound
	 * @return 介于origin和bound之间的整型
	 */
	public static int nextInt(final int origin, final int bound) {
		return current().nextInt(origin, bound);
	}

	/**
	 * 获取一个随机长整型.
	 *
	 * @return 一个随机长整型
	 */
	public static long nextLong() {
		return current().nextLong();
	}

	/**
	 * 获取一个随机长整型.
	 *
	 * @param n 长整型最大值
	 * @return 一个随机长整型
	 */
	public static long nextLong(final long n) {
		return current().nextLong(n);
	}

	/**
	 * 返回一个介于origin和bound之间的长整型.
	 *
	 * @param origin origin
	 * @param bound  bound
	 * @return 介于origin和bound之间的长整型
	 */
	public static long nextLong(final long origin, final long bound) {
		return current().nextLong(origin, bound);
	}

	/**
	 * 获取随机布尔值.
	 *
	 * @return 随机布尔值
	 */
	public static boolean nextBoolean() {
		return current().nextBoolean();
	}

	/**
	 * 获取随机单精度浮点数.
	 *
	 * @return 随机单精度浮点数
	 */
	public static float nextFloat() {
		return current().nextFloat();
	}

	/**
	 * 获取随机双精度浮点数.
	 *
	 * @return 随机双精度浮点数
	 */
	public static double nextDouble() {
		return current().nextDouble();
	}

	/**
	 * 返回一个介于0.0和bound之间的双精度浮点数.
	 *
	 * @param bound bound
	 * @return 介于0.0和bound之间的双精度浮点数
	 */
	public static double nextDouble(final double bound) {
		return current().nextDouble(bound);
	}

	/**
	 * 返回一个介于origin和bound之间的双精度浮点数.
	 *
	 * @param origin origin
	 * @param bound  bound
	 * @return 介于origin和bound之间的双精度浮点数
	 */
	public static double nextDouble(final double origin, final double bound) {
		return current().nextDouble(origin, bound);
	}

	/**
	 * 获取随机高精度浮点数.
	 *
	 * @return 随机高精度浮点数
	 */
	public static BigDecimal nextBigDecimal() {
		return BigDecimal.valueOf(nextDouble());
	}

	/**
	 * 获取随机高精度整数.
	 *
	 * @return 随机高精度整数
	 */
	public static BigInteger nextBigInteger() {
		return BigInteger.valueOf(nextLong());
	}
}
