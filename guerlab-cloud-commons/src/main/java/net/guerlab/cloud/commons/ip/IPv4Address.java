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
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author guer
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
public class IPv4Address implements IPAddress {

    /**
     * 类型
     */
    @Setter
    @Getter
    private IPType ipType;

    /**
     * 用一个long来存储,以后可以用**其他的库**替代
     */
    @Getter
    private long ipAddress;

    public IPv4Address() {
        this(0);
    }

    public IPv4Address(String ipAddressStr) {
        this(parseIpAddress(ipAddressStr));
    }

    public IPv4Address(long address) {
        reset();
        this.ipAddress = address;
    }

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

    public void reset() {
        ipType = IPType.IPV4;
        ipAddress = 0;
    }

    public final boolean isClassA() {
        return (this.ipAddress >> 31) == 0;
    }

    public final boolean isClassB() {
        return (this.ipAddress >> 30) == 2;
    }

    public final boolean isClassC() {
        return (this.ipAddress >> 29) == 6;
    }

    private static long parseIpAddress(String ipAddressStr) {
        ipAddressStr = StringUtils.trimToNull(ipAddressStr);
        long result = 0;
        if (ipAddressStr == null) {
            throw new IllegalArgumentException();
        }

        String ex = ipAddressStr;
        long offset = 24;

        int index;
        long number;
        for (number = 0; number < 3; ++number) {
            index = ex.indexOf('.');
            if (index == -1) {
                throw new IllegalArgumentException("Invalid IP Address [" + ipAddressStr + "]");
            }

            String numberStr = ex.substring(0, index);
            long number1 = Integer.parseInt(numberStr);
            if (number1 < 0 || number1 > 255) {
                throw new IllegalArgumentException("Invalid IP Address [" + ipAddressStr + "]");
            }

            result += number1 << offset;
            offset -= 8;
            ex = ex.substring(index + 1);
        }

        if (StringUtils.isBlank(ex)) {
            throw new IllegalArgumentException("Invalid IP Address [" + ipAddressStr + "]");
        }
        if (!NumberUtils.isCreatable(ex)) {
            throw new IllegalArgumentException("Invalid IP Address [" + ipAddressStr + "]");
        }
        number = Integer.parseInt(ex);
        if (number >= 0 && number <= 255) {
            result += number << offset;
        } else {
            throw new IllegalArgumentException("Invalid IP Address [" + ipAddressStr + "]");
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IPv4Address that = (IPv4Address) o;

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
