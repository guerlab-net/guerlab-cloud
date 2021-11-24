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

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

/**
 * 可排序实体
 *
 * @param <E>
 *         对象类型
 * @author guer
 */
@Schema(name = "BaseOrderEntity", description = "可排序实体")
public abstract class BaseOrderEntity<E extends BaseOrderEntity<?>> extends BaseEntity implements IOrderEntity<E> {

    /**
     * 排序值
     */
    @Schema(description = "排序值")
    protected Integer orderNum;

    @Nullable
    @Override
    public Integer getOrderNum() {
        return orderNum;
    }

    @Override
    public void setOrderNum(@Nullable Integer orderNum) {
        this.orderNum = orderNum;
    }
}
