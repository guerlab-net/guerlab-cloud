/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.commons.ip;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * ipv4地址
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Getter
public class Ipv4Address implements IpAddress, Ipv4 {

    /**
     * 类型
     */
    private final IpType ipType = IpType.IPV4;

    /**
     * 用一个long来存储,以后可以用**其他的库**替代
     */
    private final long ipAddress;

    public Ipv4Address() {
        this(0);
    }

    public Ipv4Address(String ipAddressStr) {
        this(parseIpAddress(ipAddressStr));
    }

    public Ipv4Address(long address) {
        this.ipAddress = address;
    }

    /**
     * 将数值类型地址转变为字符串格式地址
     *
     * @param ipAddress
     *         数值类型地址
     * @return 字符串格式地址
     */
    public static String convertString(long ipAddress) {
        StringBuilder result = new StringBuilder();
        long temp;
        temp = ipAddress >> 24 & 255;
        result.append(temp);
        result.append(".");
        temp = ipAddress >> 16 & 255;
        result.append(temp);
        result.append(".");
        temp = ipAddress >> 8 & 255;
        result.append(temp);
        result.append(".");
        temp = ipAddress & 255;
        result.append(temp);
        return result.toString();
    }

    /**
     * 解析字符串格式地址
     *
     * @param ipAddressStr
     *         字符串格式地址
     * @return 数值类型地址
     */
    public static long parseIpAddress(String ipAddressStr) {
        ipAddressStr = StringUtils.trimToNull(ipAddressStr);
        if (ipAddressStr == null) {
            throw new IllegalArgumentException();
        }

        String ex = ipAddressStr;
        long offset = 24;
        long result = 0;
        int index;
        for (int segments = 0; segments < SEGMENTS_SIZE; ++segments) {
            index = ex.indexOf('.');
            if (index == -1) {
                throwException(ipAddressStr);
            }

            String numberStr = ex.substring(0, index);
            long number1 = Integer.parseInt(numberStr);
            valueCheck(ipAddressStr, number1);

            result += number1 << offset;
            offset -= 8;
            ex = ex.substring(index + 1);
        }

        if (StringUtils.isBlank(ex) || !NumberUtils.isCreatable(ex)) {
            throwException(ipAddressStr);
        }
        long number = Integer.parseInt(ex);
        valueCheck(ipAddressStr, number);
        result += number << offset;

        return result;
    }

    private static void valueCheck(String ipAddressStr, long value) {
        if (value < 0 || value > MAX_VALUE) {
            throwException(ipAddressStr);
        }
    }

    private static void throwException(String ipAddressStr) {
        throw new IllegalArgumentException("Invalid IP Address [" + ipAddressStr + "]");
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
        int result = 271;
        result = 31 * result + (int) (ipAddress ^ (ipAddress >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return convertString(this.ipAddress);
    }
}
