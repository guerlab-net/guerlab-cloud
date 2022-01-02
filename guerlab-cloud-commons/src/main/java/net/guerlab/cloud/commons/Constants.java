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
package net.guerlab.cloud.commons;

/**
 * 常量
 *
 * @author guer
 */
@SuppressWarnings("unused")
public interface Constants {

    /**
     * 默认上级ID
     */
    Long DEFAULT_PARENT_ID = 0L;

    /**
     * 默认排序值
     */
    Integer DEFAULT_ORDER_NUM = 0;

    /**
     * TOKEN
     */
    String TOKEN = "Authorization";

    /**
     * 空ID
     */
    Long EMPTY_ID = 0L;

    /**
     * 空名称
     */
    String EMPTY_NAME = "";

    /**
     * 请求方法
     */
    String REQUEST_METHOD = "Request-Method";

    /**
     * 请求URI
     */
    String REQUEST_URI = "Request-Uri";

    /**
     * 完整请求路径
     */
    String COMPLETE_REQUEST_URI = "Complete-Request-Uri";

    /**
     * 当前操作者
     */
    String CURRENT_OPERATOR = "currentOperator";
}
