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

package net.guerlab.cloud.commons.entity;

/**
 * 实体字段名称
 *
 * @author guer
 */
public interface EntityColumnNames {

    /**
     * 字段名称-ID
     */
    String ID = "ID";

    /**
     * 字段名称-创建时间
     */
    String CREATED_TIME = "CREATED_TIME";

    /**
     * 字段名称-最后修改时间
     */
    String LAST_UPDATED_TIME = "LAST_UPDATED_TIME";

    /**
     * 字段名称-创建人
     */
    String CREATED_BY = "CREATED_BY";

    /**
     * 字段名称-最后修改时间
     */
    String MODIFIED_BY = "MODIFIED_BY";

    /**
     * 字段名称-逻辑删除标识
     */
    String DELETED = "DELETED";

    /**
     * 字段名称-乐观锁版本
     */
    String VERSION = "VERSION";

    /**
     * 字段名称-排序值
     */
    String ORDER_NUM = "ORDER_NUM";
}
