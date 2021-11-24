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

package net.guerlab.cloud.commons.api;

import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.searchparams.SearchParams;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * APi定义
 *
 * @param <D>
 *         返回实体类型
 * @param <PK>
 *         主键类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface Api<D, PK extends Serializable, SP extends SearchParams> {

    /**
     * 参数名-分页ID
     */
    String PARAM_NAME_PAGE_ID = "pageId";
    /**
     * 参数名-分页尺寸
     */
    String PARAM_NAME_PAGE_SIZE = "pageSize";
    /**
     * 参数默认值-分页ID
     */
    String PARAM_DEFAULT_PAGE_ID = "1";
    /**
     * 参数默认值-分页尺寸
     */
    String PARAM_DEFAULT_PAGE_SIZE = "10";

    /**
     * 查询单一结果，根据搜索参数进行筛选
     *
     * @param searchParams
     *         搜索参数对象
     * @return 实体
     */
    @Nullable
    @PostMapping("/one")
    D selectOne(@RequestBody SP searchParams);

    /**
     * 查询单一结果，根据搜索参数进行筛选
     *
     * @param searchParams
     *         搜索参数对象
     * @return Optional
     */
    default Optional<D> selectOneOptional(SP searchParams) {
        return Optional.ofNullable(selectOne(searchParams));
    }

    /**
     * 通过Id查询单一结果
     *
     * @param id
     *         主键id
     * @return 实体
     */
    @Nullable
    @GetMapping("/{id}")
    D selectById(@PathVariable(value = "id") PK id);

    /**
     * 通过Id查询单一结果
     *
     * @param id
     *         主键id
     * @return Optional
     */
    default Optional<D> selectByIdOptional(PK id) {
        return Optional.ofNullable(selectById(id));
    }

    /**
     * 获取列表
     *
     * @param searchParams
     *         搜索参数对象
     * @return 实体列表
     */
    @PostMapping
    Collection<D> selectList(@RequestBody SP searchParams);

    /**
     * 查询分页列表
     *
     * @param searchParams
     *         搜索参数对象
     * @param pageId
     *         分页ID
     * @param pageSize
     *         分页尺寸
     * @return 实体分页列表
     */
    @PostMapping("/page")
    Pageable<D> selectPage(@RequestBody SP searchParams,
            @RequestParam(name = PARAM_NAME_PAGE_ID, defaultValue = PARAM_DEFAULT_PAGE_ID, required = false) int pageId,
            @RequestParam(name = PARAM_NAME_PAGE_SIZE, defaultValue = PARAM_DEFAULT_PAGE_SIZE, required = false) int pageSize);

    /**
     * 查询总记录数
     *
     * @param searchParams
     *         搜索参数对象
     * @return 实体总数
     */
    @PostMapping("/count")
    int selectCount(@RequestBody SP searchParams);
}
