package net.guerlab.smart.platform.commons.util;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.dto.ConvertDTO;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;

/**
 * Bean转换工具类
 *
 * @author guer
 */
@SuppressWarnings({ "unused" })
public class BeanConvertUtils {

    private BeanConvertUtils() {
    }

    /**
     * 转换为DTO
     *
     * @param <D>
     *         DTO类型
     * @param <E>
     *         实体类型
     * @param entity
     *         实体
     * @return DTO
     */
    public static <D, E extends ConvertDTO<D>> D toObject(E entity) {
        return entity == null ? null : entity.toDTO();
    }

    /**
     * 转换为DTO
     *
     * @param <D>
     *         DTO类型
     * @param <E>
     *         实体类型
     * @param entity
     *         实体
     * @param dtoClass
     *         DTO类型
     * @return DTO
     */
    public static <D, E> D toObject(E entity, Class<D> dtoClass) {
        if (entity == null) {
            return null;
        }

        D dto;
        try {
            dto = dtoClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }

        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    /**
     * 转换为DTO列表
     *
     * @param <D>
     *         DTO类型
     * @param <E>
     *         实体类型
     * @param entityList
     *         实体列表
     * @return DTO列表
     */
    public static <D, E extends ConvertDTO<D>> List<D> toList(Collection<E> entityList) {
        return CollectionUtil.toList(entityList, ConvertDTO::toDTO);
    }

    /**
     * 转换为DTO列表
     *
     * @param <D>
     *         DTO类型
     * @param <E>
     *         实体类型
     * @param entityList
     *         实体列表
     * @param dtoClass
     *         DTO类型
     * @return DTO列表
     */
    public static <D, E> List<D> toList(Collection<E> entityList, Class<D> dtoClass) {
        return CollectionUtil.toList(entityList, e -> toObject(e, dtoClass));
    }

    /**
     * 转换为DTO列表对象
     *
     * @param <D>
     *         DTO类型
     * @param <E>
     *         实体类型
     * @param list
     *         实体列表对象
     * @return DTO列表对象
     */
    public static <D, E extends ConvertDTO<D>> ListObject<D> toListObject(ListObject<E> list) {
        if (list == null || CollectionUtil.isEmpty(list.getList())) {
            return ListObject.empty();
        }

        ListObject<D> result = copyListObject(list);

        result.setList(toList(list.getList()));

        return result;
    }

    /**
     * 转换为DTO列表对象
     *
     * @param <D>
     *         DTO类型
     * @param <E>
     *         实体类型
     * @param list
     *         实体列表对象
     * @param dtoClass
     *         DTO类型
     * @return DTO列表对象
     */
    public static <D, E> ListObject<D> toListObject(ListObject<E> list, Class<D> dtoClass) {
        if (list == null || CollectionUtil.isEmpty(list.getList())) {
            return ListObject.empty();
        }

        ListObject<D> result = copyListObject(list);

        result.setList(toList(list.getList(), dtoClass));

        return result;
    }

    private static <D> ListObject<D> copyListObject(ListObject<?> origin) {
        ListObject<D> result = new ListObject<>();
        result.setPageSize(origin.getPageSize());
        result.setCount(origin.getCount());
        result.setCurrentPageId(origin.getCurrentPageId());
        result.setMaxPageId(origin.getMaxPageId());

        return result;
    }

}
