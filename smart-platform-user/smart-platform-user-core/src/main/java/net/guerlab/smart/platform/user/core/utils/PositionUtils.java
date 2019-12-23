package net.guerlab.smart.platform.user.core.utils;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.user.core.domain.IPosition;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static net.guerlab.smart.platform.commons.Constants.EMPTY_ID;

/**
 * 职位工具类
 *
 * @author guer
 */
public class PositionUtils {

    public static final String LINK_CHARACTER = ":";

    public static final String ALL_DEPARTMENT_POSITION = EMPTY_ID + LINK_CHARACTER + EMPTY_ID;

    private PositionUtils() {

    }

    /**
     * 根据职位信息列表获取关键字列表
     *
     * @param list
     *         职位信息列表
     * @return 关键字列表
     */
    public static Set<String> getKeys(Collection<? extends IPosition> list) {
        if (list == null || list.isEmpty()) {
            return Collections.singleton(ALL_DEPARTMENT_POSITION);
        }

        Set<String> departmentDuties = CollectionUtil.toSet(list, PositionUtils::getDepartmentDuty);
        Set<String> departmentIds = CollectionUtil.toSet(list, PositionUtils::getDepartment);
        Set<String> dutyIds = CollectionUtil.toSet(list, PositionUtils::getDuty);

        Set<String> keys = new HashSet<>(departmentDuties.size() + departmentIds.size() + dutyIds.size() + 1);
        keys.add(ALL_DEPARTMENT_POSITION);
        keys.addAll(departmentDuties);
        keys.addAll(departmentIds);
        keys.addAll(dutyIds);

        return keys;
    }

    /**
     * 根据部门ID和职务ID格式化关键字
     *
     * @param departmentId
     *         部门ID
     * @param dutyId
     *         职务ID
     * @return 关键字
     */
    public static String format(Long departmentId, Long dutyId) {
        return getValue(departmentId) + ":" + getValue(dutyId);
    }

    private static String getDepartmentDuty(IPosition position) {
        return format(position.getDepartmentId(), position.getDutyId());
    }

    private static String getDepartment(IPosition position) {
        return format(position.getDepartmentId(), EMPTY_ID);
    }

    private static String getDuty(IPosition position) {
        return format(EMPTY_ID, position.getDutyId());
    }

    private static Long getValue(Long value) {
        return NumberHelper.greaterZero(value) ? value : EMPTY_ID;
    }
}
