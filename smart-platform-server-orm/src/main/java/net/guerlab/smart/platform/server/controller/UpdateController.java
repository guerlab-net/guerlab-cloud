package net.guerlab.smart.platform.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.spring.commons.dto.Convert;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * 基础更新控制器接口
 *
 * @param <D>
 *         DTO类型
 * @param <E>
 *         实体类型
 * @param <S>
 *         Service类型
 * @param <PK>
 *         主键类型
 * @author guer
 */
public interface UpdateController<D, E extends Convert<D>, S extends BaseService<E, PK, SP>, SP extends AbstractSearchParams, PK extends Serializable>
        extends IController<E, S, PK>, ModifyControllerWrapper<D, E, PK> {

    /**
     * 根据主键ID编辑对象
     *
     * @param id
     *         主键ID
     * @param dto
     *         dto对象
     * @return 编辑后的dto对象
     */
    @Operation(summary = "编辑")
    @PutMapping("/{id}")
    default D update(@Parameter(description = "id", required = true) @PathVariable PK id,
            @Parameter(description = "对象数据", required = true) @RequestBody D dto) {
        E entity = findOne0(id);
        beforeUpdate(entity, dto);
        copyProperties(dto, entity, id);
        getService().updateById(entity);
        afterUpdate(entity, dto);
        return getService().selectById(id).convert();
    }

    /**
     * 编辑前置
     *
     * @param entity
     *         实体
     * @param dto
     *         dto对象
     */
    default void beforeUpdate(E entity, D dto) {

    }

    /**
     * 编辑后置
     *
     * @param entity
     *         实体
     * @param dto
     *         dto对象
     */
    default void afterUpdate(E entity, D dto) {

    }

}
