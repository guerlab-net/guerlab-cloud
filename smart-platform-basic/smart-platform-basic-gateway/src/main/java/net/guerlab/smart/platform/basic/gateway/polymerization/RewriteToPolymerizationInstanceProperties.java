package net.guerlab.smart.platform.basic.gateway.polymerization;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 单服务配置
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
@Data
public class RewriteToPolymerizationInstanceProperties implements IRewriteToPolymerizationProperties {

    /**
     * 是否启用
     */
    private Boolean enable = true;

    /**
     * 聚合服务名称列表
     */
    private List<String> serviceNames = new ArrayList<>();
}
