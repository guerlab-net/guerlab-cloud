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
package net.guerlab.cloud.commons.util;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.commons.entity.BaseOrderTreeEntity;
import net.guerlab.commons.collection.CollectionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 树结构工具类
 *
 * @author guer
 */
@SuppressWarnings("unused")
public class TreeUtils {

    private TreeUtils() {

    }

    /**
     * 树型格式化
     *
     * @param data
     *         数据列表
     * @param <T>
     *         树形排序对象
     * @return 树结构列表
     */
    public static <T extends BaseOrderTreeEntity<T>> Collection<T> tree(Collection<T> data) {
        if (CollectionUtil.isEmpty(data)) {
            return Collections.emptyList();
        }

        Map<Long, T> map = CollectionUtil.toMap(data, BaseOrderTreeEntity::id);
        Map<Long, List<T>> childrenMap = CollectionUtil.group(data, BaseOrderTreeEntity::parentId);

        List<T> tops = childrenMap.get(Constants.DEFAULT_PARENT_ID);

        if (CollectionUtil.isEmpty(tops)) {
            return Collections.emptyList();
        }

        childrenMap.forEach((parentId, list) -> {
            Collections.sort(list);

            T parent = map.get(parentId);

            if (parent != null) {
                parent.setChildren(list);
            }
        });

        return tops;
    }
}
