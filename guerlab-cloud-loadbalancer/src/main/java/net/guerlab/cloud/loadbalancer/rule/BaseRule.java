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
package net.guerlab.cloud.loadbalancer.rule;

import net.guerlab.cloud.loadbalancer.properties.BaseRuleProperties;

/**
 * 策略
 *
 * @author guer
 */
public abstract class BaseRule<P extends BaseRuleProperties> implements IRule {

    /**
     * 配置
     */
    protected final P properties;

    /**
     * 通过配置初始化策略
     *
     * @param properties
     *         配置
     */
    public BaseRule(P properties) {
        this.properties = properties;
    }

    @Override
    public boolean isEnabled() {
        return properties.isEnabled();
    }

    @Override
    public int getOrder() {
        return properties.getOrder();
    }
}
