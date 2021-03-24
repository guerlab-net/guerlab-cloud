package net.guerlab.smart.platform.server.controller;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.commons.util.ParameterizedTypeUtils;
import net.guerlab.smart.platform.server.service.BaseFindService;
import net.guerlab.spring.commons.dto.Convert;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * 基础控制器
 *
 * @param <D>
 *         DTO对象类型
 * @param <E>
 *         实体对象类型
 * @param <S>
 *         服务接口类型
 * @param <PK>
 *         实体主键类型
 * @author guer
 */
@Slf4j
public abstract class AbstractControllerImpl<D, E extends Convert<D>, S extends BaseFindService<E, PK, SP>, SP extends AbstractSearchParams, PK extends Serializable>
        implements IController<E, S, PK> {

    /**
     * 服务接口
     */
    protected S service;

    @Override
    public S getService() {
        return service;
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setService(S service) {
        this.service = service;
    }

    /**
     * 根据主键ID查询实体
     *
     * @param id
     *         主键ID
     * @return 实体
     */
    @Override
    public E findOne0(PK id) {
        E entity = getService().selectById(id);

        if (entity == null) {
            ApplicationException exception = nullPointException();
            throw exception != null ? exception : new NullPointerException();
        }

        return entity;
    }

    /**
     * 初始化新实体对象
     *
     * @return 实体对象
     */
    @Override
    public E newEntity() {
        Class<E> clazz = ParameterizedTypeUtils.getByClass(this, 1);

        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage(), e);
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 当对象为空的时候抛出的异常
     *
     * @return 当对象为空的时候抛出的异常
     */
    protected ApplicationException nullPointException() {
        return null;
    }

}
