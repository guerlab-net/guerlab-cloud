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
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等
     * <p>
     * 不支持主键策略，插入前需要设置好主键的值
     * <p>
     * 特别注意：2018-04-22 后，该方法支持 @KeySql 注解的 genId 方式
     *
     * @param recordList
     *         待插入数据
     * @return insert result
     */
    int replaceInsertList(@Param("list") List<T> recordList);
}
