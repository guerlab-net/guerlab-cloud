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
import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.AbstractSearchParams;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * 抽象快速失败
 *
 * @author guer
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractFallback<D, PK extends Serializable, SP extends AbstractSearchParams>
        implements Api<D, PK, SP> {

    protected final Throwable cause;

    @Nullable
    @Override
    public D selectOne(SP searchParams) {
        log.error("selectOne fallback", cause);
        return null;
    }

    @Nullable
    @Override
    public D selectById(PK id) {
        log.error("selectById fallback", cause);
        return null;
    }

    @Override
    public Collection<D> selectList(SP searchParams) {
        log.error("selectList fallback", cause);
        return Collections.emptyList();
    }

    @Override
    public Pageable<D> selectPage(SP searchParams) {
        log.error("selectPage fallback", cause);
        return Pageable.empty();
    }

    @Override
    public int selectCount(SP searchParams) {
        log.error("selectCount fallback", cause);
        return 0;
    }
}
