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
