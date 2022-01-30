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
import net.guerlab.cloud.commons.entity.TreeEntity;
import net.guerlab.cloud.commons.entity.TreeNode;
import net.guerlab.commons.collection.CollectionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * @param <E>
     *         树节点类型
     * @return 树结构列表
     */
    public static <E extends TreeNode<Long>> List<TreeEntity<E, Long>> tree(Collection<E> data) {
        return tree(data, Constants.DEFAULT_PARENT_ID);
    }

    /**
     * 树型格式化
     *
     * @param data
     *         数据列表
     * @param rootId
     *         根ID
     * @param <E>
     *         树节点类型
     * @param <PK>
     *         主键类型
     * @return 树结构列表
     */
    public static <E extends TreeNode<PK>, PK> List<TreeEntity<E, PK>> tree(Collection<E> data, PK rootId) {
        if (CollectionUtil.isEmpty(data)) {
            return Collections.emptyList();
        }

        List<TreeEntity<E, PK>> treeEntities = data.stream().sorted((o1, o2) -> {
            if (o1 instanceof IOrderlyEntity && o2 instanceof IOrderlyEntity) {
                return IOrderlyEntity.compareTo((IOrderlyEntity<?>) o1, (IOrderlyEntity<?>) o2);
            } else {
                return 0;
            }
        }).map(TreeUtils::buildTreeEntity).collect(Collectors.toList());

        Map<PK, TreeEntity<E, PK>> map = CollectionUtil.toMap(treeEntities, TreeEntity::getId);
        Map<PK, List<TreeEntity<E, PK>>> childrenMap = CollectionUtil.group(treeEntities, TreeEntity::getParentId);

        List<TreeEntity<E, PK>> roots = childrenMap.get(rootId);

        if (CollectionUtil.isEmpty(roots)) {
            return Collections.emptyList();
        }

        childrenMap.forEach((parentId, list) -> {
            TreeEntity<E, PK> parent = map.get(parentId);

            if (parent != null) {
                parent.setChildren(list);
            }
        });

        return roots;
    }

    /**
     * 构造树形结构对象
     *
     * @param entity
     *         树节点
     * @param <E>
     *         树节点类型
     * @param <PK>
     *         主键类型
     * @return 树形结构对象
     */
    private static <E extends TreeNode<PK>, PK> TreeEntity<E, PK> buildTreeEntity(E entity) {
        TreeEntity<E, PK> treeEntity = new TreeEntity<>();
        treeEntity.setId(entity.id());
        treeEntity.setParentId(entity.parentId());
        treeEntity.setLabel(entity.label());
        treeEntity.setObject(entity);

        return treeEntity;
    }
}
