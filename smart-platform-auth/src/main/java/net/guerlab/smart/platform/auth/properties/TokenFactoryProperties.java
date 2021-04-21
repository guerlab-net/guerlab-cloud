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
package net.guerlab.smart.platform.auth.properties;

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
