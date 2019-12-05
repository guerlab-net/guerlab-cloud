package net.guerlab.smart.platform.server.utils;

import net.guerlab.commons.collection.CollectionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 批量保存工具
 *
 * @author guer
 */
public class BatchSaveUtils {

    private BatchSaveUtils() {

    }

    /**
     * 批量保存过滤
     *
     * @param collection
     *         待保存集合
     * @param <T>
     *         实体类型
     * @return 过滤后可保存集合
     */
    public static <T> List<T> filter(Collection<T> collection, Function<? super T, ? extends T> filter) {
        if (CollectionUtil.isEmpty(collection)) {
            return Collections.emptyList();
        }

        List<T> list = collection.stream().map(filter).filter(Objects::nonNull).collect(Collectors.toList());

        return CollectionUtil.isEmpty(list) ? Collections.emptyList() : list;
    }
}
