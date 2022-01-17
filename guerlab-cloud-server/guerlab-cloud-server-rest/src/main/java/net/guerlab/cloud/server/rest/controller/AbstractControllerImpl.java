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

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.commons.util.ParameterizedTypeUtils;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.service.BaseFindService;
import net.guerlab.commons.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础控制器
 *
 * @param <D>
 *         输出对象类型
 * @param <E>
 *         实体对象类型
 * @param <S>
 *         服务接口类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
@Slf4j
public abstract class AbstractControllerImpl<D, E, S extends BaseFindService<E, SP>, SP extends SearchParams>
        implements IController<D, E, S> {

    /**
     * 服务接口
     */
    protected S service;

    @Override
    public S getService() {
        return service;
    }

    /**
     * 设置服务实例
     *
     * @param service
     *         服务实例
     */
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
    public E findOne0(Long id) {
        E entity = getService().selectById(id);

        if (entity == null) {
            throw nullPointException();
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

}
