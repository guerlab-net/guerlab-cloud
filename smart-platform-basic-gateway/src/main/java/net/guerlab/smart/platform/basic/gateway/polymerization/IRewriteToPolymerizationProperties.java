package net.guerlab.smart.platform.basic.gateway.polymerization;

import java.util.List;

/**
 * 重新请求至聚合服务配置接口
 *
 * @author guer
 */
public interface IRewriteToPolymerizationProperties {

    /**
     * 获取是否启用
     *
     * @return 是否启用
     */
    Boolean getEnable();

    /**
     * 获取聚合服务名称列表
     *
     * @return 聚合服务名称列表
     */
    List<String> getServiceNames();
}
