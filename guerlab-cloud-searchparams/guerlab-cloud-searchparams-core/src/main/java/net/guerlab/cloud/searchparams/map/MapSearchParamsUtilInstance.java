/*
 * Copyright 2018-2021 guerlab.net and other contributors.
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
package net.guerlab.cloud.searchparams.map;

import net.guerlab.cloud.searchparams.AbstractSearchParams;
import net.guerlab.cloud.searchparams.AbstractSearchParamsUtilInstance;
import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.SearchParamsHandler;
import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * Map处理实例
 *
 * @author guer
 */
public class MapSearchParamsUtilInstance extends AbstractSearchParamsUtilInstance {

    /**
     * 分页关键字-页面ID
     */
    public static final String KEY_PAGE_ID = "pageId";

    /**
     * 分页关键字-页面尺寸
     */
    public static final String KEY_PAGE_SIZE = "pageSize";

    /**
     * 分页关键字-偏移量
     */
    public static final String KEY_PAGE_OFFSET = "pageOffset";

    /**
     * 分页关键字-起始位索引
     */
    public static final String KEY_PAGE_START_INDEX = "pageStartIndex";

    /**
     * 分页关键字-结束位索引
     */
    public static final String KEY_PAGE_END_INDEX = "pageEndIndex";

    private static final Class<?> CLAZZ = Map.class;

    public MapSearchParamsUtilInstance() {
        setDefaultHandler(new DefaultHandler());
    }

    @Override
    public boolean accept(Object object) {
        return CLAZZ.isInstance(object);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void afterHandler(AbstractSearchParams searchParams, Object object) {
        Map map = (Map) object;

        int pageId = Math.max(searchParams.getPageId(), 1);
        int pageSize = searchParams.getPageSize();
        int pageOffset = (pageId - 1) * pageSize;
        int pageEndIndex = pageOffset + pageSize;

        map.put(KEY_PAGE_ID, pageId);
        map.put(KEY_PAGE_SIZE, pageSize);
        map.put(KEY_PAGE_OFFSET, pageOffset);
        map.put(KEY_PAGE_START_INDEX, pageOffset);
        map.put(KEY_PAGE_END_INDEX, pageEndIndex);
    }

    private static class DefaultHandler implements SearchParamsHandler {

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public void setValue(Object object, String fieldName, String columnName, Object value,
                SearchModelType searchModelType, @Nullable String customSql) {
            ((Map) object).put(fieldName, value);
        }
    }
}
