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
package net.guerlab.smart.platform.commons.ip;

/**
 * IP地址
 *
 * @author guer
 */
public interface IPAddress {

    /**
     * 获取IP类型
     *
     * @return IP类型
     */
    IPType getIpType();

    /**
     * 设置IP类型
     *
     * @param ipType
     *         IP类型
     */
    void setIpType(IPType ipType);
}
