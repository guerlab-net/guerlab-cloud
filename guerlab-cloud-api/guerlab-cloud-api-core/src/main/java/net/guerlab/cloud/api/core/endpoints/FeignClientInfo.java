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
 * feign客户端实例信息
 *
 * @author guer
 */
@Data
public class FeignClientInfo {

    /**
     * bean名称
     */
    private String beanName;

    /**
     * 类路径
     */
    private String classPath;

    /**
     * 解析后的请求路径
     */
    private String url;

    /**
     * 注解信息
     */
    private FeignClientAnnotationInfo annotation;
}