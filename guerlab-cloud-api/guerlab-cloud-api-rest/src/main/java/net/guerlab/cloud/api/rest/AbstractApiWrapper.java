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

import lombok.RequiredArgsConstructor;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.AbstractSearchParams;
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
@RequiredArgsConstructor
public abstract class AbstractApiWrapper<D, PK extends Serializable, SP extends AbstractSearchParams, A extends Api<D, PK, SP>>
        implements Api<D, PK, SP> {

    protected final A api;

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
    public Pageable<D> selectPage(SP searchParams) {
        return api.selectPage(searchParams);
    }

    @Override
    public int selectCount(SP searchParams) {
        return api.selectCount(searchParams);
    }
}
