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

package net.guerlab.cloud.api.rest;

import net.guerlab.cloud.commons.api.Api;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.SearchParams;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Collection;

/**
 * api包装
 *
 * @param <D>
 *         返回实体类型
 * @param <PK>
 *         主键类型
 * @param <SP>
 *         搜索参数类型
 * @param <A>
 *         api类型
 * @author guer
 */
public abstract class AbstractApiWrapper<D, PK extends Serializable, SP extends SearchParams, A extends Api<D, PK, SP>>
        implements Api<D, PK, SP> {

    /**
     * api
     */
    protected final A api;

    /**
     * 根据接口实例初始化
     *
     * @param api
     *         接口实例
     */
    public AbstractApiWrapper(A api) {
        this.api = api;
    }

    @Nullable
    @Override
    public D selectOne(SP searchParams) {
        return api.selectOne(searchParams);
    }

    @Nullable
    @Override
    public D selectById(PK id) {
        return api.selectById(id);
    }

    @Override
    public Collection<D> selectList(SP searchParams) {
        return api.selectList(searchParams);
    }

    @Override
    public Pageable<D> selectPage(SP searchParams, int pageId, int pageSize) {
        return api.selectPage(searchParams, pageId, pageSize);
    }

    @Override
    public int selectCount(SP searchParams) {
        return api.selectCount(searchParams);
    }
}
