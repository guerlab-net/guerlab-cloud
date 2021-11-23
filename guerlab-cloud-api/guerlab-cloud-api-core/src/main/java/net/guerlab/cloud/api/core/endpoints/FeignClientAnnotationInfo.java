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
package net.guerlab.cloud.api.core.endpoints;

import lombok.Data;

/**
 * feign客户端注解信息
 *
 * @author guer
 */
@Data
public class FeignClientAnnotationInfo {

    /**
     * feign注解上的name
     */
    private String name;

    /**
     * feign注解上的contextId
     */
    private String contextId;

    /**
     * feign注解上的url
     */
    private String url;

    /**
     * feign注解上的decode404
     */
    private boolean decode404;

    /**
     * feign注解上的path
     */
    private String path;

    /**
     * feign注解上的primary
     */
    private boolean primary;
}
