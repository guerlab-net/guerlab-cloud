package net.guerlab.cloud.commons.ip;

/**
 * ipv4
 *
 * @author guer
 */
public interface Ipv4 extends IpAddress {

    /**
     * 分割符正则表达式
     */
    String SEPARATOR_REG = "\\.";

    /**
     * IP格式
     */
    String FORMAT = "%d.%d.%d.%d";

    /**
     * 范围链接符标志
     */
    String RANGE_LINK_FLAG = "-";

    /**
     * 子网掩码标志
     */
    String MASK_FLAG = "/";

    /**
     * 最大值
     */
    int MAX_VALUE = 255;

    /**
     * 分组长度
     */
    int GROUP_SIZE = 4;

    /**
     * 最大子网长度
     */
    int MAX_MASK = 32;

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
    default boolean contains(Ipv4Address target) {
        long address = target.getIpAddress();
        return getStartAddress() <= address && address <= getEndAddress();
    }
}
