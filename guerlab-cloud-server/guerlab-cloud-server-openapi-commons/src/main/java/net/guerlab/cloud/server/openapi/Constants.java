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
package net.guerlab.cloud.server.openapi;

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
    String CLOUD_PREFIX = "${springdoc.cloud.path:#{T(net.guerlab.cloud.server.openapi.Constants).DEFAULT_CLOUD_PREFIX}}";

    /**
     * cloud环境下相关配置-网关映射路径
     */
    String CLOUD_GATEWAY_PATH = "${springdoc.cloud.gateway-path:${spring.application.name:application}}";
}
