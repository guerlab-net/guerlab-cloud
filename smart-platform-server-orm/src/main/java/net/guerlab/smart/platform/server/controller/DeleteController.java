package net.guerlab.smart.platform.server.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.spring.commons.dto.ConvertDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 基础删除控制器接口
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
public interface DeleteController<D, E extends ConvertDTO<D>, S extends BaseService<E, PK>, PK>
        extends IController<E, S, PK> {

    /**
     * 根据主键ID删除对象
     *
     * @param id
     *         主键ID
     * @param force
     *         强制删除标志
     */
    @ApiOperation("删除")
    @DeleteMapping("/{id}")
    default void delete(@ApiParam(value = "id", required = true) @PathVariable PK id,
            @ApiParam(value = "强制删除标志") @RequestParam(required = false) Boolean force) {
        findOne0(id);
        getService().deleteById(id, force);
    }

}
