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

package net.guerlab.cloud.commons;

/**
 * 正则表达式常量.
 *
 * @author guer
 */
@SuppressWarnings("unused")
public final class RegexConstants {

	/**
	 * 邮箱正则表达式.
	 */
	public static final String EMAIL_REG = "^[\\.a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

	/**
	 * 电话号码正则表达式.
	 */
	public static final String PHONE_REG = "^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}$";

	private RegexConstants() {
	}
}
