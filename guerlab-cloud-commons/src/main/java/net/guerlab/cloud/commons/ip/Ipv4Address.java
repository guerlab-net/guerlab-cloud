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

import java.util.Objects;

import lombok.Getter;

/**
 * ipv4地址.
 *
 * @author guer
 */
@Getter
public class Ipv4Address extends Ipv4 implements IpSingleAddress {

	/**
	 * 类型.
	 */
	private static final IpType IP_TYPE = IpType.IPV4;

	/**
	 * 用一个long来存储,以后可以用**其他的库**替代.
	 */
	private final long ipAddress;

	/**
	 * 根据IPv4地址字符串构造IPv4地址对象.
	 *
	 * @param ipAddressStr IPv4地址字符串
	 */
	Ipv4Address(String ipAddressStr) {
		this(IpUtils.parseIpv4Address(ipAddressStr));
	}

	/**
	 * 根据IPv4地址构造IPv4地址对象.
	 *
	 * @param address IPv4地址
	 */
	Ipv4Address(long address) {
		this.ipAddress = address;
	}

	@Override
	public IpType getIpType() {
		return IP_TYPE;
	}

	@Override
	public long getStartAddress() {
		return ipAddress;
	}

	@Override
	public long getEndAddress() {
		return getStartAddress();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Ipv4Address that = (Ipv4Address) o;

		return ipAddress == that.ipAddress;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(ipAddress);
	}

	@Override
	public String toString() {
		return IpUtils.convertIpv4String(this.ipAddress);
	}
}
