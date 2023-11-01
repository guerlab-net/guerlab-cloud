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

package net.guerlab.cloud.commons.ip;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import static net.guerlab.cloud.commons.ip.Ipv4.BASE_FORMAT_REG;
import static net.guerlab.cloud.commons.ip.Ipv4.FORMAT;
import static net.guerlab.cloud.commons.ip.Ipv4.GROUP_SIZE;
import static net.guerlab.cloud.commons.ip.Ipv4.MASK_FLAG;
import static net.guerlab.cloud.commons.ip.Ipv4.MASK_VALUE_REG;
import static net.guerlab.cloud.commons.ip.Ipv4.MAX_MASK;
import static net.guerlab.cloud.commons.ip.Ipv4.MAX_VALUE;
import static net.guerlab.cloud.commons.ip.Ipv4.RANGE_END_REG;
import static net.guerlab.cloud.commons.ip.Ipv4.RANGE_GROUP_LIMIT;
import static net.guerlab.cloud.commons.ip.Ipv4.RANGE_LINK_FLAG;
import static net.guerlab.cloud.commons.ip.Ipv4.SEPARATOR_REG;
import static net.guerlab.cloud.commons.ip.Ipv4.VALUE_REG;
import static net.guerlab.cloud.commons.ip.Ipv4.WITH_MASK_FORMAT_REG;

/**
 * IPv4地址工具类.
 *
 * @author guer
 */
public final class Ipv4Utils {

	private Ipv4Utils() {

	}

	/**
	 * 判断是否为ipv4格式字符串.
	 *
	 * @param ip ip地址
	 * @return 是否为ipv4格式
	 */
	public static boolean isIpv4(String ip) {
		return isSingleIpv4(ip) || isSegmentIpv4(ip);
	}

	/**
	 * 判断是否为单个ipv4地址格式字符串.
	 *
	 * @param ip ip地址
	 * @return 是否为单个ipv4地址格式字符串
	 */
	public static boolean isSingleIpv4(String ip) {
		return ip.matches(BASE_FORMAT_REG);
	}

	/**
	 * 判断是否为范围ipv4地址格式字符串.
	 *
	 * @param ip ip地址
	 * @return 是否为范围ipv4地址格式字符串
	 */
	public static boolean isSegmentIpv4(String ip) {
		boolean hasRangeLinkFlag = ip.contains(RANGE_LINK_FLAG);
		boolean hasMaskFlag = ip.contains(MASK_FLAG);

		if (!hasRangeLinkFlag && !hasMaskFlag) {
			return false;
		}
		else if (!hasRangeLinkFlag) {
			// support format: 1.2.3.4/32
			return ip.matches(WITH_MASK_FORMAT_REG);
		}

		String[] addressArray = ip.split(RANGE_LINK_FLAG);
		if (addressArray.length != RANGE_GROUP_LIMIT) {
			return false;
		}

		if (hasMaskFlag) {
			// support format: 1.2.3.4/24-1.3.3.4/24, 1.2.3.4-1.3.3.4/24, 1.2.3.4/24-1.3.3.4
			return Arrays.stream(addressArray).anyMatch(address -> address.matches(WITH_MASK_FORMAT_REG));
		}
		else {
			// support format: 1.2.3.4-255; 1.2.3.4-1.2.4.5
			boolean startMatch = addressArray[0].matches(BASE_FORMAT_REG);
			boolean endMatch = addressArray[1].matches(RANGE_END_REG);
			return startMatch && endMatch;
		}
	}

	/**
	 * 解析ipv4地址.
	 *
	 * @param address IP地址
	 * @return IP地址
	 */
	public static Ipv4 parseIpv4(long address) {
		return new Ipv4Address(address);
	}

	/**
	 * 解析ipv4地址.
	 *
	 * @param address IP地址
	 * @return IP地址
	 */
	public static Ipv4 parseIpv4(String address) {
		address = StringUtils.trimToNull(address);
		if (address == null) {
			throw new IllegalArgumentException("address invalid");
		}

		boolean hasRangeLinkFlag = address.contains(RANGE_LINK_FLAG);
		boolean hasMaskFlag = address.contains(MASK_FLAG);

		if (hasRangeLinkFlag && hasMaskFlag) {
			// support format: 1.2.3.4/24-1.3.3.4/24, 1.2.3.4-1.3.3.4/24, 1.2.3.4/24-1.3.3.4
			String[] addressArray = address.split(RANGE_LINK_FLAG);
			if (addressArray.length != RANGE_GROUP_LIMIT) {
				throw new IllegalArgumentException("address is invalid");
			}
			Ipv4 start = parseIpv4(addressArray[0]);
			Ipv4 end = parseIpv4(addressArray[1]);
			return new Ipv4RangeAddress(start.getStartAddress(), end.getEndAddress());
		}
		else if (hasRangeLinkFlag) {
			// support format: 1.2.3.4-255; 1.2.3.4-1.2.4.5
			return parseIpv4WithRangeLinkFlag(address);
		}
		else if (hasMaskFlag) {
			// support format: 1.2.3.4/32
			return parseIpv4WithMaskFlag(address);
		}
		else {
			return new Ipv4Address(address);
		}
	}

	/**
	 * 基于范围链接符标志的解析.
	 *
	 * @param ipRange ip范围
	 * @return ipv4地址段
	 */
	public static Ipv4RangeAddress parseIpv4WithRangeLinkFlag(String ipRange) {
		String[] ipRangeArray = ipRange.split(RANGE_LINK_FLAG, RANGE_GROUP_LIMIT);
		long start = parseIpv4Address(ipRangeArray[0]);

		String[] groupValues = ipRangeArray[1].split(SEPARATOR_REG, GROUP_SIZE);
		int length = groupValues.length;
		long baseAddress = start & (0x0FFFFFFFFL << (length << 3));
		int offset = 0;
		while (offset < length) {
			baseAddress += parseIpv4GroupValue(groupValues[offset]) << ((length - offset - 1) << 3);
			offset++;
		}

		return new Ipv4RangeAddress(start, baseAddress);
	}

	/**
	 * 基于子网掩码标志的解析.
	 *
	 * @param ipRange ip范围
	 * @return ipv4地址段
	 */
	public static Ipv4RangeAddress parseIpv4WithMaskFlag(String ipRange) {
		String[] ipRangeArray = ipRange.split(MASK_FLAG, 2);
		if (!ipRangeArray[1].matches(MASK_VALUE_REG)) {
			throw new IllegalArgumentException("illegal arguments");
		}
		int maskLength = Integer.parseInt(ipRangeArray[1]);
		long tmp = parseIpv4Address(ipRangeArray[0]);
		// 子网掩码
		Ipv4Address subNetMask = computeIpv4MaskFromNetworkPrefix(maskLength);
		// 子网
		Ipv4Address subNet = new Ipv4Address(subNetMask.getIpAddress() & tmp);
		// 设置起止地址
		Ipv4Address start = new Ipv4Address(subNet.getIpAddress());
		Ipv4Address end = new Ipv4Address(subNet.getIpAddress() + (0x00000001L << (MAX_MASK - maskLength)) - 1);

		return new Ipv4RangeAddress(start.getStartAddress(), end.getIpAddress(), subNetMask.getIpAddress(), maskLength);
	}

	/**
	 * 解析ipv4分组地址.
	 *
	 * @param str ipv4分组地址
	 * @return 地址值
	 */
	private static long parseIpv4GroupValue(String str) {
		if (!str.matches(VALUE_REG)) {
			throw new IllegalArgumentException(str + " is invalid");
		}
		return Long.parseLong(str);
	}

	/**
	 * 通过掩码长度构造IPv4掩码地址.
	 *
	 * @param prefix 掩码长度
	 * @return IPv4掩码地址
	 */
	public static Ipv4Address computeIpv4MaskFromNetworkPrefix(int prefix) {
		String str = "1".repeat(prefix) + "0".repeat(32 - prefix);
		return new Ipv4Address(Long.parseLong(str, 2));
	}

	/**
	 * 通过起始地址和结束地址构造IPv4掩码地址.
	 *
	 * @param startAddress 起始地址
	 * @param endAddress   结束地址
	 * @return IPv4掩码地址
	 */
	public static long calculationIpv4Mask(long startAddress, long endAddress) {
		long mask = 0L;
		int offset;
		for (int i = 0; i < GROUP_SIZE; i++) {
			offset = i << 3;
			mask += (~((startAddress >> offset & 0xFF) ^ (endAddress >> offset & 0xFF)) & 0x0FF) << offset;
		}
		return mask;
	}

	/**
	 * 将数值类型地址转变为字符串格式地址.
	 *
	 * @param ipAddress 数值类型地址
	 * @return 字符串格式地址
	 */
	public static String convertIpv4String(long ipAddress) {
		return String.format(FORMAT, ipAddress >> 24 & MAX_VALUE, ipAddress >> 16 & MAX_VALUE,
				ipAddress >> 8 & MAX_VALUE, ipAddress & MAX_VALUE);
	}

	/**
	 * 解析IPv4字符串格式地址.
	 *
	 * @param ipAddressStr 字符串格式地址
	 * @return 数值类型地址
	 */
	public static long parseIpv4Address(String ipAddressStr) {
		ipAddressStr = StringUtils.trimToNull(ipAddressStr);
		if (ipAddressStr == null) {
			throw new IllegalArgumentException();
		}

		long offset = 24;
		long result = 0;
		for (String valStr : ipAddressStr.split(SEPARATOR_REG, GROUP_SIZE)) {
			if (!valStr.matches(VALUE_REG)) {
				throw new IllegalArgumentException("Invalid IP Address [" + ipAddressStr + "]");
			}
			result += Long.parseLong(valStr) << offset;
			offset -= 8;
		}

		return result;
	}
}
