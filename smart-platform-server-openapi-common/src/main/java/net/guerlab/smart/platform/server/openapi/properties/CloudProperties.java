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
