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
package net.guerlab.cloud.server.rest.controller;

import net.guerlab.cloud.commons.entity.BaseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;

/**
 * 提供控制器层的数据修改增强
 *
 * @param <D>
 *         DTO类型
 * @param <E>
 *         实体类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface ModifyControllerWrapper<D, E> {

    /**
     * 拷贝属性
     *
     * @param dto
     *         dto对象
     * @param entity
     *         实体对象
     * @param id
     *         主键
     */
    default void copyProperties(D dto, E entity, @Nullable Long id) {
        if (entity instanceof BaseEntity) {
            BaseEntity tempEntity = (BaseEntity) entity;
            Long version = tempEntity.getVersion();
            BeanUtils.copyProperties(dto, entity);
            tempEntity.setVersion(version);

            if (id != null) {
                tempEntity.setId(id);
            }
        } else {
            BeanUtils.copyProperties(dto, entity);
        }
    }

}
