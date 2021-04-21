package net.guerlab.smart.platform.server.openapi.properties;

import lombok.Data;
import net.guerlab.smart.platform.server.openapi.Constants;

/**
 * cloud环境下相关配置
 *
 * @author guer
 */
@Data
public class CloudProperties {

    /**
     * 请求根路径
     */
    private String path = Constants.DEFAULT_CLOUD_PREFIX;

    /**
     * 网关映射路径
     */
    private String gatewayPath;
}
