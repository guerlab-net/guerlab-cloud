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

/**
 * IP地址
 *
 * @author guer
 */
public interface IpAddress {

    /**
     * 范围链接符标志
     */
    String RANGE_LINK_FLAG = "-";

    /**
     * 分组限制数量
     */
    int RANGE_GROUP_LIMIT = 2;

    /**
     * 子网掩码标志
     */
    String MASK_FLAG = "/";

    /**
     * 获取IP类型
     *
     * @return IP类型
     */
    IpType getIpType();

    /**
     * 获取开始位置
     *
     * @return 开始位置
     */
    long getStartAddress();

    /**
     * 获取结束位置
     *
     * @return 结束位置
     */
    long getEndAddress();

    /**
     * 判断是否包含某个IP
     *
     * @param target
     *         目标IP
     * @return 是否包含
     */
    default boolean contains(IpSingleAddress target) {
        long address = target.getIpAddress();
        return getStartAddress() <= address && address <= getEndAddress();
    }
}
