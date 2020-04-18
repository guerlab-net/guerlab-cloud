package net.guerlab.smart.platform.server.controller;

import net.guerlab.smart.platform.commons.entity.BaseEntity;
import net.guerlab.spring.commons.dto.ConvertDTO;
import org.springframework.beans.BeanUtils;

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
public interface ModifyControllerWrapper<D, E extends ConvertDTO<D>, PK> {

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
