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
package net.guerlab.cloud.server.utils;

import net.guerlab.commons.collection.CollectionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 批量保存工具
 *
 * @author guer
 */
public class BatchSaveUtils {

    private BatchSaveUtils() {

    }

    /**
     * 批量保存过滤
     *
     * @param collection
     *         待保存集合
     * @param filter
     *         批量保存过滤器
     * @param <T>
     *         实体类型
     * @return 过滤后可保存集合
     */
    public static <T> List<T> filter(Collection<T> collection, Function<? super T, ? extends T> filter) {
        if (CollectionUtil.isEmpty(collection)) {
            return Collections.emptyList();
        }

        List<T> list = collection.stream().map(filter).filter(Objects::nonNull).collect(Collectors.toList());

        return CollectionUtil.isEmpty(list) ? Collections.emptyList() : list;
    }
}