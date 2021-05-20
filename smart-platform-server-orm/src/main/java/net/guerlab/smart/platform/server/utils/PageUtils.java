package net.guerlab.smart.platform.server.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.guerlab.smart.platform.server.service.QueryWrapperGetter;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.web.result.ListObject;

import java.util.Collection;

/**
 * 分页工具类
 *
 * @author guer
 */
public class PageUtils {

    private PageUtils() {

    }

    /**
     * 查询分页列表
     *
     * @param wrapperGetter
     *         QueryWrapper获取接口对象
     * @param searchParams
     *         搜索参数对象
     * @param mapper
     *         mapper对象
     * @param <T>
     *         实体类型
     * @param <SP>
     *         搜索参数对象类型
     * @return 分页结果列表
     */
    public static <T, SP extends AbstractSearchParams> ListObject<T> selectPage(QueryWrapperGetter<T, SP> wrapperGetter,
            SP searchParams, BaseMapper<T> mapper) {
        int pageId = Math.max(searchParams.getPageId(), 1);
        int pageSize = searchParams.getPageSize();

        QueryWrapper<T> queryWrapper = wrapperGetter.getQueryWrapperWithSelectMethod(searchParams);

        Page<T> result = mapper.selectPage(new Page<>(pageId, pageSize), queryWrapper);
        Collection<T> list = result.getRecords();

        long total = result.getTotal();

        ListObject<T> listObject = new ListObject<>(searchParams.getPageSize(), total, list);

        listObject.setCurrentPageId(pageId);
        listObject.setMaxPageId((long) Math.ceil((double) total / pageSize));

        return listObject;
    }
}
