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

package net.guerlab.cloud.core.properties;

import java.util.List;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 数值json格式化配置,用于将数值类型在json格式化处理的时候处理为字符串类型,避免精度溢出.
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties("spring.jackson.format")
public class NumberJsonStringFormatProperties {

	/**
	 * 是否格式化所有数值类型.
	 */
	private boolean formatAllNumber;

	/**
	 * 是否格式化BigDecimal.
	 */
	private boolean formatBigDecimal;

	/**
	 * 是否格式化BigInteger.
	 */
	private boolean formatBigInteger;

	/**
	 * 是否格式化Byte包装类.
	 */
	private boolean formatByteClass;

	/**
	 * 是否格式化Byte基本类型.
	 */
	private boolean formatByteType;

	/**
	 * 是否格式化Short包装类.
	 */
	private boolean formatShortClass;

	/**
	 * 是否格式化Short基本类型.
	 */
	private boolean formatShortType;

	/**
	 * 是否格式化Integer包装类.
	 */
	private boolean formatIntegerClass;

	/**
	 * 是否格式化Integer基本类型.
	 */
	private boolean formatIntegerType;

	/**
	 * 是否格式化Long包装类.
	 */
	private boolean formatLongClass;

	/**
	 * 是否格式化Long基本类型.
	 */
	private boolean formatLongType;

	/**
	 * 是否格式化Float包装类.
	 */
	private boolean formatFloatClass;

	/**
	 * 是否格式化Float基本类型.
	 */
	private boolean formatFloatType;

	/**
	 * 是否格式化Double包装类.
	 */
	private boolean formatDoubleClass;

	/**
	 * 是否格式化Double基本类型.
	 */
	private boolean formatDoubleType;

	/**
	 * 待格式化数值类型类.
	 */
	private List<Class<? extends Number>> formatNumberClassList;
}
