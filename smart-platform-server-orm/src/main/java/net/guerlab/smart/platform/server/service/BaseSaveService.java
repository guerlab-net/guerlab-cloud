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
package net.guerlab.smart.platform.server.service;

import net.guerlab.spring.searchparams.AbstractSearchParams;

/**
 * 基本保存服务接口
 *
 * @param <T>
 *         数据类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
public interface BaseSaveService<T, SP extends AbstractSearchParams> extends QueryWrapperGetter<T, SP> {

    /**
     * 添加
     *
     * @param entity
     *         实体
     */
    void insert(T entity);

}
