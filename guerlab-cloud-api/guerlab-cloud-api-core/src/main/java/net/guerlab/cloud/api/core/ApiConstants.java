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
package net.guerlab.cloud.api.core;

/**
 * API常量
 *
 * @author guer
 */
@SuppressWarnings("unused")
public interface ApiConstants {

    /**
     * 服务ID
     */
    String SERVICE_ID_PROPERTIES_PREFIX = "guerlab.cloud.api.names";

    /**
     * 默认API服务后缀
     */
    String DEFAULT_API_SERVICE_SUFFIX = "-api";

    /**
     * 默认内部服务后缀
     */
    String DEFAULT_INTERNAL_SERVICE_SUFFIX = "-internal";

    /**
     * 默认公共服务后缀
     */
    String DEFAULT_COMMONS_SERVICE_SUFFIX = "-commons";
}
