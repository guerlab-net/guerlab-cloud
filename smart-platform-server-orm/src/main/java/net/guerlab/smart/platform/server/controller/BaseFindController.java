package net.guerlab.smart.platform.server.controller;

import net.guerlab.smart.platform.server.service.BaseFindService;
import net.guerlab.spring.commons.dto.Convert;
import net.guerlab.spring.searchparams.AbstractSearchParams;

import java.io.Serializable;

/**
 * 基础查询控制器
 *
 * @param <D>
 *         DTO对象类型
 * @param <E>
 *         实体对象类型
 * @param <S>
 *         服务接口类型
 * @param <SP>
 *         搜索参数对象类型
 * @param <PK>
 *         实体主键类型
 * @author guer
 */
public abstract class BaseFindController<D, E extends Convert<D>, S extends BaseFindService<E, PK, SP>, SP extends AbstractSearchParams, PK extends Serializable>
        extends AbstractControllerImpl<D, E, S, SP, PK> implements FindController<D, E, S, SP, PK> {

}
