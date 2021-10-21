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
package net.guerlab.cloud.server.rest.controller;

import java.io.Serializable;

/**
 * 控制器接口
 *
 * @param <E>
 *         实体对象类型
 * @param <S>
 *         服务接口类型
 * @param <PK>
 *         实体主键类型
 * @author guer
 */
public interface IController<E, S, PK extends Serializable> {

    /**
     * 获取服务对象
     *
     * @return 服务对象
     */
    S getService();

    /**
     * 创建新实体
     *
     * @return 实体
     */
    E newEntity();

    /**
     * 查询对象
     *
     * @param id
     *         主键id
     * @return 对象
     */
    E findOne0(PK id);
}
