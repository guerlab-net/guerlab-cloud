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
package net.guerlab.smart.platform.server.mappers;

import java.util.Collections;

/**
 * 重新插入mapper
 *
 * @param <T>
 *         实体类型
 * @author guer
 */
public interface ReplaceInsertMapper<T> extends BatchMapper<T> {

    /**
     * 保存一个实体
     *
     * @param record
     *         实体
     * @return 影响行数
     */
    default int replaceInsert(T record) {
        return replaceInsertList(Collections.singletonList(record));
    }
}
