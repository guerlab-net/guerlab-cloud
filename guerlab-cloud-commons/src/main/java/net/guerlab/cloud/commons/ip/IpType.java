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

package net.guerlab.cloud.commons.ip;

/**
 * ip类型.
 *
 * @author guer
 */
public enum IpType {
	/**
	 * IPV4.
	 */
	IPV4,
	/**
	 * IPV4段.
	 * <p>
	 * example: 10.1.1.1-244, 10.1.1.1-10.1.1.244
	 */
	IPV4_SEGMENT,
	/**
	 * IPV6.
	 */
	IPV6,
	/**
	 * IPV6段.
	 */
	IPV6_SEGMENT;

	/**
	 * 判断是否为ipv4.
	 *
	 * @param ipType ip类型
	 * @return 否为ipv4
	 */
	public static boolean isIpv4(IpType ipType) {
		return ipType == IPV4 || ipType == IPV4_SEGMENT;
	}

	/**
	 * 判断是否为ipv6.
	 *
	 * @param ipType ip类型
	 * @return 否为ipv6
	 */
	public static boolean isIpv6(IpType ipType) {
		return ipType == IPV6 || ipType == IPV6_SEGMENT;
	}

	/**
	 * 判断是否为单个IP.
	 *
	 * @param ipType ip类型
	 * @return 是否为单个IP
	 */
	public static boolean isSingleIp(IpType ipType) {
		return ipType == IPV4 || ipType == IPV6;
	}

	/**
	 * 判断是否为IP范围段.
	 *
	 * @param ipType ip类型
	 * @return 是否为IP范围段
	 */
	public static boolean isIpSegment(IpType ipType) {
		return ipType == IPV4_SEGMENT || ipType == IPV6_SEGMENT;
	}
}
