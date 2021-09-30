package net.guerlab.cloud.commons.ip;

/**
 * ipv4
 *
 * @author guer
 */
public interface Ipv4 extends IpAddress {

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
     * 分割符正则表达式
     */
    String SEPARATOR_REG = "\\.";

    /**
     * IPv4格式
     */
    String FORMAT = "%d.%d.%d.%d";

    /**
     * 单段有效值格式
     */
    String VALUE_FORMAT = "((25[0-5])|(2[0-4]\\d)|([0-1]?\\d?\\d))";

    /**
     * 单段有效值正则表达式
     */
    String VALUE_REG = "^" + VALUE_FORMAT + "$";

    /**
     * 掩码有效值格式
     */
    String MASK_VALUE_FORMAT = "((3[0-2])|([1-2]\\d)|([0]?[1-9]))";

    /**
     * 掩码有效值正则表达式
     */
    String MASK_VALUE_REG = "^" + MASK_VALUE_FORMAT + "$";

    /**
     * 基础IPv4格式格式
     */
    String BASE_FORMAT_FORMAT = "(" + VALUE_FORMAT + "(\\." + VALUE_FORMAT + "){3})";

    /**
     * 基础IPv4格式正则表达式
     */
    String BASE_FORMAT_REG = "^" + BASE_FORMAT_FORMAT + "$";

    /**
     * 带子网掩码IPv4格式
     */
    String WITH_MASK_FORMAT_FORMAT = "(" + BASE_FORMAT_FORMAT + "(/" + MASK_VALUE_FORMAT + ")" + "?)";

    /**
     * 带子网掩码IPv4正则表达式
     */
    String WITH_MASK_FORMAT_REG = "^" + WITH_MASK_FORMAT_FORMAT + "$";

    /**
     * 范围IPv4格式
     */
    String RANGE_END_FORMAT = "(" + VALUE_FORMAT + "\\.){0,3}(" + VALUE_FORMAT + ")";

    /**
     * 范围IPv4正则表达式
     */
    String RANGE_END_REG = "^" + RANGE_END_FORMAT + "$";
}
