package net.guerlab.cloud.loadbalancer.rule;

import net.guerlab.cloud.loadbalancer.properties.BaseRuleProperties;

/**
 * 策略
 *
 * @author guer
 */
public abstract class BaseRule<P extends BaseRuleProperties> implements IRule {

    /**
     * 排序
     */
    protected P properties;

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
