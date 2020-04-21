package net.guerlab.smart.platform.server.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.spring.commons.dto.ConvertDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
public interface UpdateController<D, E extends ConvertDTO<D>, S extends BaseService<E, PK>, PK>
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
    @ApiOperation("编辑")
    @PutMapping("/{id}")
    default D update(@ApiParam(value = "id", required = true) @PathVariable PK id,
            @ApiParam(value = "对象数据", required = true) @RequestBody D dto) {
        E entity = findOne0(id);
        beforeUpdate(entity, dto);
        copyProperties(dto, entity, id);
        getService().updateSelectiveById(entity);
        afterUpdate(entity, dto);
        return getService().selectById(id).toDTO();
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
