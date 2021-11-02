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

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 批量处理mapper
 *
 * @param <T>
 *         实体类型
 * @author guer
 */
public interface BatchMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入
     * <p>
     * 不支持主键策略，插入前需要设置好主键的值
     * <p>
     *
     * @param recordList
     *         待插入数据
     * @return insert result
     */
    int replaceInsertList(@Param("list") List<T> recordList);

    /**
     * 批量插入或更新
     * <p>
     * 不支持主键策略，插入前需要设置好主键的值
     * <p>
     *
     * @param recordList
     *         待插入数据
     * @return insert result
     */
    int insertOrUpdateList(@Param("list") List<T> recordList);
}
