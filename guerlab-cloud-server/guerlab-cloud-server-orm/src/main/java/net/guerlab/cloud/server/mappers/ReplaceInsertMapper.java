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
package net.guerlab.cloud.server.mappers;

import java.util.Collections;

/**
 * 重新插入mapper
 *
 * @param <T>
 *         实体类型
 * @author guer
 */
@SuppressWarnings("unused")
public interface ReplaceInsertMapper<T> extends BatchMapper<T> {

    /**
     * 保存一个实体
     *
     * @param entity
     *         实体
     * @return 影响行数
     */
    default int replaceInsert(T entity) {
        return replaceInsertList(Collections.singletonList(entity));
    }

    /**
     * 插入或更新一个实体
     *
     * @param entity
     *         实体
     * @return 影响行数
     */
    default int insertOrUpdate(T entity) {
        return insertOrUpdateList(Collections.singletonList(entity));
    }
}
