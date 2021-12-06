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
package net.guerlab.cloud.commons.util;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.commons.entity.IOrderlyEntity;
import net.guerlab.cloud.commons.entity.ITreeEntity;
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
    public static <T extends ITreeEntity<T, Long>> List<T> tree(Collection<T> data) {
        return tree(data, Constants.DEFAULT_PARENT_ID);
    }

    /**
     * 树型格式化
     *
     * @param data
     *         数据列表
     * @param rootId
     *         根ID
     * @param <T>
     *         树形排序对象
     * @param <PK>
     *         主键类型
     * @return 树结构列表
     */
    public static <T extends ITreeEntity<T, PK>, PK> List<T> tree(Collection<T> data, PK rootId) {
        if (CollectionUtil.isEmpty(data)) {
            return Collections.emptyList();
        }

        Map<PK, T> map = CollectionUtil.toMap(data, ITreeEntity::id);
        Map<PK, List<T>> childrenMap = CollectionUtil.group(data, ITreeEntity::parentId);

        List<T> roots = childrenMap.get(rootId);

        if (CollectionUtil.isEmpty(roots)) {
            return Collections.emptyList();
        }

        childrenMap.forEach((parentId, list) -> {
            list.sort((o1, o2) -> {
                if (o1 instanceof IOrderlyEntity && o2 instanceof IOrderlyEntity) {
                    return IOrderlyEntity.compareTo((IOrderlyEntity<?>) o1, (IOrderlyEntity<?>) o2);
                } else {
                    return 0;
                }
            });

            T parent = map.get(parentId);

            if (parent != null) {
                parent.setChildren(list);
            }
        });

        return roots;
    }
}
