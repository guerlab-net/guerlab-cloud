package net.guerlab.smart.platform.server.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.spring.commons.dto.ConvertDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 基础保存控制器接口
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
public interface SaveController<D, E extends ConvertDTO<D>, S extends BaseService<E, PK>, PK>
        extends IController<E, S, PK>, ModifyControllerWrapper<D, E, PK> {

    /**
     * 添加对象
     *
     * @param dto
     *         dto对象
     * @return 添加的dto对象
     */
    @ApiOperation("添加")
    @PostMapping
    default D save(@ApiParam(value = "对象数据", required = true) @RequestBody D dto) {
        E entity = newEntity();
        beforeSave(dto);
        copyProperties(dto, entity, null);
        getService().insert(entity);
        afterSave(entity, dto);
        return entity.toDTO();
    }

    /**
     * 保存前置
     *
     * @param dto
     *         dto对象
     */
    default void beforeSave(D dto) {

    }

    /**
     * 保存后置
     *
     * @param entity
     *         实体
     * @param dto
     *         dto对象
     */
    default void afterSave(E entity, D dto) {

    }

}
