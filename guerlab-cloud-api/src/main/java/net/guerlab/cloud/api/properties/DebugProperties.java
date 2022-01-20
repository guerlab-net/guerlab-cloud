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
package net.guerlab.cloud.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Collections;
import java.util.List;

/**
 * 开发模式配置
 *
 * @author guer
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = DebugProperties.PROPERTIES_PREFIX)
public class DebugProperties {

    /**
     * 配置前缀
     */
    public static final String PROPERTIES_PREFIX = "spring.cloud.debug";

    /**
     * 启用标志
     */
    private boolean enable;

    /**
     * 网关代理地址
     */
    private String gatewayProxyUrl = "http://gateway/proxy/";

    /**
     * 代理请求头名
     */
    private String proxyHeaderName = "GUERLAB-PROXY";

    /**
     * 代理请求头值
     */
    private String proxyHeaderValue = "GUERLAB-PROXY";

    /**
     * 本地服务列表
     */
    private List<String> localServices = Collections.emptyList();
}
