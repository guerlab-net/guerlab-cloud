package net.guerlab.smart.platform.server.mappers;

import net.guerlab.smart.platform.server.provider.ReplaceInsertListProvider;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 批量操作Mapper
 *
 * @param <T>
 *         实体类型
 * @author guer
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface BatchMapper<T> extends Mapper<T>, InsertListMapper<T> {

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
    @SuppressWarnings("UnusedReturnValue")
    @InsertProvider(type = ReplaceInsertListProvider.class, method = "dynamicSQL")
    int replaceInsertList(List<? extends T> recordList);
}
