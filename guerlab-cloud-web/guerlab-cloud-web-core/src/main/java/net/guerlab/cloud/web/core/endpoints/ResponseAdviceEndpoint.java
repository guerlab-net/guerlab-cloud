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
package net.guerlab.cloud.web.core.endpoints;

import net.guerlab.cloud.web.core.properties.ResponseAdvisorProperties;
import net.guerlab.commons.collection.CollectionUtil;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 响应环绕监控端点
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Endpoint(id = "response-advice")
public class ResponseAdviceEndpoint {

    private final ResponseAdvisorProperties properties;

    public ResponseAdviceEndpoint(ResponseAdvisorProperties properties) {
        this.properties = properties;
    }

    @ReadOperation
    public ResponseAdvisorProperties get() {
        return properties;
    }

    @WriteOperation
    public void set(@Nullable ResponseAdvisorProperties properties) {
        if (properties == null) {
            return;
        }
        List<String> excluded = properties.getExcluded();
        if (!CollectionUtil.isEmpty(excluded)) {
            this.properties.addExcluded(excluded);
        }
    }
}
