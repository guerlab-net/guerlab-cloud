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
import net.guerlab.cloud.commons.util.BeanConvertUtils;
import net.guerlab.cloud.core.dto.Convert;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.AbstractSearchParams;
import net.guerlab.cloud.server.service.BaseFindService;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Collection;

/**
 * 本地服务包装
 *
 * @param <D>
 *         返回实体类型
 * @param <PK>
 *         主键类型
 * @param <SP>
 *         搜索参数类型
 * @param <T>
 *         实体类型
 * @param <S>
 *         服务类型
 * @author guer
 */
@RequiredArgsConstructor
public abstract class AbstractLocalServiceWrapper<D, PK extends Serializable, SP extends AbstractSearchParams, T extends Convert<D>, S extends BaseFindService<T, PK, SP>>
        implements Api<D, PK, SP> {

        protected final S service;

        @Nullable
        @Override
        public D selectOne(SP searchParams) {
                T entity = service.selectOne(searchParams);
                return entity == null ? null : entity.convert();
        }

        @Nullable
        @Override
        public D selectById(PK id) {
                T entity = service.selectById(id);
                return entity == null ? null : entity.convert();
        }

        @Override
        public Collection<D> selectList(SP searchParams) {
                return BeanConvertUtils.toList(service.selectList(searchParams));
        }

        @Override
        public Pageable<D> selectPage(SP searchParams) {
                return BeanConvertUtils.toPageable(service.selectPage(searchParams));
        }

        @Override
        public int selectCount(SP searchParams) {
                return service.selectCount(searchParams);
        }
}
