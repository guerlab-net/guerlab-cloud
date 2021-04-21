package net.guerlab.smart.platform.server.openapi;

/**
 * @author guer
 */
public interface Constants {

    /**
     * cloud环境下相关配置-默认请求根路径
     */
    String DEFAULT_CLOUD_PREFIX = "/openapi-cloud";

    /**
     * cloud环境下相关配置-请求根路径
     */
    String CLOUD_PREFIX = "${springdoc.cloud.path:#{T(net.guerlab.smart.platform.server.openapi.Constants).DEFAULT_CLOUD_PREFIX}}";

    /**
     * cloud环境下相关配置-网关映射路径
     */
    String CLOUD_GATEWAY_PATH = "${springdoc.cloud.gateway-path:${spring.application.name:application}}";
}
