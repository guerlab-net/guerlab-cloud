/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * ipv4地址段
 *
 * @author guer
 */
@Getter
@Slf4j
public class Ipv4RangeAddress implements Ipv4, IpRangeAddress {

    /**
     * 类型
     */
    private final IpType ipType = IpType.IPV4_SEGMENT;

    /**
     * 起始IP
     */
    private final long startAddress;

    /**
     * 结束IP
     */
    private final long endAddress;

    /**
     * 子网掩码
     */
    private final long mask;

    /**
     * 子网掩码长度
     */
    private final int maskLength;

    /**
     * 通过开始地址、结束地址、掩码地址、掩码长度构造ipv4范围地址
     *
     * @param startAddress
     *         开始地址
     * @param endAddress
     *         结束地址
     * @param mask
     *         掩码地址
     * @param maskLength
     *         掩码长度
     */
    Ipv4RangeAddress(long startAddress, long endAddress, long mask, int maskLength) {
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.mask = mask;
        this.maskLength = maskLength;
    }

    /**
     * 通过开始地址、结束地址构造ipv4范围地址
     *
     * @param startAddress
     *         开始地址
     * @param endAddress
     *         结束地址
     */
    Ipv4RangeAddress(long startAddress, long endAddress) {
        this(startAddress, endAddress, IpUtils.calculationIpv4Mask(startAddress, endAddress), -1);
    }

    @Override
    public String toString() {
        if (maskLength > 0) {
            return "Ipv4RangeAddress[ipWithMask=" + IpUtils.convertIpv4String(startAddress) + "/" + maskLength + "]";
        } else {
            return "Ipv4RangeAddress[start=" + IpUtils.convertIpv4String(startAddress) + ", end=" + IpUtils
                    .convertIpv4String(endAddress) + ", mask=" + IpUtils.convertIpv4String(mask) + "]";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ipv4RangeAddress range = (Ipv4RangeAddress) o;

        return Objects.equals(startAddress, range.startAddress) && Objects.equals(endAddress, range.endAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startAddress, endAddress);
    }
}
