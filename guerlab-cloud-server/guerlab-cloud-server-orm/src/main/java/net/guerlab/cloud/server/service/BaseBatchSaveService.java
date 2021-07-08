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
package net.guerlab.cloud.server.service;

import net.guerlab.spring.searchparams.AbstractSearchParams;

import java.util.Collection;

/**
 * 基本批量保存服务接口
 *
 * @param <T>
 *         数据类型
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public interface BaseBatchSaveService<T, SP extends AbstractSearchParams> extends QueryWrapperGetter<T, SP> {

    /**
     * 批量保存
     *
     * @param collection
     *         待保存列表
     * @return 已保存列表
     */
    Collection<T> batchInsert(Collection<T> collection);

    /**
     * 批量保存
     *
     * @param collection
     *         待保存列表
     * @return 已保存列表
     */
    Collection<T> replaceBatchInsert(Collection<T> collection);

}
