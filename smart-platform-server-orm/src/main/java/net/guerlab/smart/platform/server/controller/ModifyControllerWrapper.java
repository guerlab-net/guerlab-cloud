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
package net.guerlab.smart.platform.server.controller;

import net.guerlab.smart.platform.commons.entity.BaseEntity;
import net.guerlab.spring.commons.dto.Convert;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 提供控制器层的数据修改增强
 *
 * @param <D>
 *         DTO类型
 * @param <E>
 *         实体类型
 * @param <PK>
 *         主键类型
 * @author guer
 */
public interface ModifyControllerWrapper<D, E extends Convert<D>, PK extends Serializable> {

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
    default void copyProperties(D dto, E entity, PK id) {
        if (entity instanceof BaseEntity) {
            BaseEntity tempEntity = (BaseEntity) entity;
            Long version = tempEntity.getVersion();
            BeanUtils.copyProperties(dto, entity);
            tempEntity.setVersion(version);
        } else {
            BeanUtils.copyProperties(dto, entity);
        }
    }

}
