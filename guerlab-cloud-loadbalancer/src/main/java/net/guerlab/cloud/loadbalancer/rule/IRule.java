package net.guerlab.cloud.loadbalancer.rule;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 策略
 *
 * @author guer
 */
public interface IRule extends Ordered, Comparable<IRule> {

    /**
     * 是否启用
     *
     * @return 是否启用
     */
    boolean isEnabled();

    /**
     * 选择实例
     *
     * @param instances
     *         实例列表
     * @param request
     *         请求
     * @return 实例列表
     */
    @Nullable
    List<ServiceInstance> choose(List<ServiceInstance> instances, Request<?> request);

    /**
     * 默认排序方式
     *
     * @param other
     *         其他策略
     * @return 排序结果
     */
    @Override
    default int compareTo(IRule other) {
        return getOrder() - other.getOrder();
    }
}
