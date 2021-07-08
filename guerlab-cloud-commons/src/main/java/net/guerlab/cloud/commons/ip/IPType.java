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

/**
 * ip类型
 *
 * @author guer
 */
public enum IPType {
    /**
     * 未知
     */
    UNKNOWN,
    /**
     * IPV4
     */
    IPV4,
    /**
     * IPV6
     */
    IPV6,
    /**
     * IPV4段
     * <p>
     * example: 10.1.1.1-244, 10.1.1.1-10.1.1.244
     */
    IPV4_SEGMENT,
    /**
     * IPV4段带掩码
     * <p>
     * example: 10.1.1.1/24
     */
    IPV4_SEGMENT_WITH_MASK,
    /**
     * IPV6段
     */
    IPV6_SEGMENT,
    /**
     * IPV6段带掩码
     */
    IPV6_SEGMENT_WITH_MASK;

    /**
     * 判断是否为ipv4
     *
     * @param ipType
     *         ip类型
     * @return 否为ipv4
     */
    public static boolean isIpv4(IPType ipType) {
        return IPV4.equals(ipType) || IPV4_SEGMENT.equals(ipType) || IPV4_SEGMENT_WITH_MASK.equals(ipType);
    }

    /**
     * 判断是否为ipv6
     *
     * @param ipType
     *         ip类型
     * @return 否为ipv6
     */
    public static boolean isIpv6(IPType ipType) {
        return IPV6.equals(ipType) || IPV6_SEGMENT.equals(ipType) || IPV6_SEGMENT_WITH_MASK.equals(ipType);
    }

    /**
     * 判断是否为IP范围段
     *
     * @param ipType
     *         ip类型
     * @return 是否为IP范围段
     */
    public static boolean isIpSegment(IPType ipType) {
        return IPV4_SEGMENT.equals(ipType) || IPV4_SEGMENT_WITH_MASK.equals(ipType) || IPV6_SEGMENT.equals(ipType)
                || IPV6_SEGMENT_WITH_MASK.equals(ipType);
    }
}
