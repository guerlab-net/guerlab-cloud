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
