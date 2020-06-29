package net.guerlab.smart.platform.basic.auth.properties;

import lombok.Data;

import java.util.Collection;
import java.util.Collections;

/**
 * token 工厂配置
 *
 * @author guer
 */
@Data
public class TokenFactoryProperties {

    /**
     * 启用标志
     */
    private boolean enable = false;

    /**
     * 默认工厂
     */
    private boolean defaultFactory = false;

    /**
     * 排序值
     */
    private int order = 0;

    /**
     * 允许的IP列表
     */
    private Collection<String> allowIpList = Collections.emptyList();

    /**
     * 拒绝的IP列表
     */
    private Collection<String> denyIpList = Collections.emptyList();
}
