package net.guerlab.smart.platform.server.mappers;

import net.guerlab.smart.platform.server.provider.ReplaceInsertProvider;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * ReplaceInsertMapper
 *
 * @param <T>
 *         实体类型
 * @author guerss
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface ReplaceInsertMapper<T> extends Mapper<T>, InsertListMapper<T> {

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     *
     * @param record
     *         实体
     * @return 影响行数
     */
    @InsertProvider(type = ReplaceInsertProvider.class, method = "dynamicSQL")
    int replaceInsert(T record);

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param record
     *         实体
     * @return 影响行数
     */
    @InsertProvider(type = ReplaceInsertProvider.class, method = "dynamicSQL")
    int replaceInsertSelective(T record);
}
