package net.guerlab.smart.platform.user.service.service;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentDutyDistributionSearchParams;
import net.guerlab.smart.platform.user.service.entity.DepartmentDutyDistribution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 部门职务分配服务
 *
 * @author guer
 */
public interface DepartmentDutyDistributionService {

    /**
     * 根据搜索参数查询列表
     *
     * @param searchParams
     *         搜索参数
     * @return 分配列表
     */
    Collection<DepartmentDutyDistribution> find(DepartmentDutyDistributionSearchParams searchParams);

    /**
     * 判断是否存在分配记录
     *
     * @param departmentId
     *         部门ID
     * @param dutyId
     *         职务ID
     * @return 是否存在分配记录
     */
    default boolean has(Long departmentId, Long dutyId) {
        if (!NumberHelper.allGreaterZero(departmentId, dutyId)) {
            return false;
        }

        DepartmentDutyDistributionSearchParams searchParams = new DepartmentDutyDistributionSearchParams();
        searchParams.setDepartmentId(departmentId);
        searchParams.setDutyId(dutyId);

        return !find(searchParams).isEmpty();
    }

    /**
     * 通过部门ID查询职务ID集合
     *
     * @param departmentId
     *         部门ID
     * @return 职务ID集合
     */
    default Collection<Long> findDutyIdByDepartmentId(Long departmentId) {
        if (!NumberHelper.greaterZero(departmentId)) {
            return new ArrayList<>();
        }

        DepartmentDutyDistributionSearchParams searchParams = new DepartmentDutyDistributionSearchParams();
        searchParams.setDepartmentId(departmentId);

        return find(searchParams).stream().map(DepartmentDutyDistribution::getDutyId).collect(Collectors.toList());
    }

    /**
     * 通过职务ID查询部门ID集合
     *
     * @param dutyId
     *         职务ID
     * @return 部门ID集合
     */
    default Collection<Long> findDepartmentIdByDutyId(Long dutyId) {
        if (!NumberHelper.greaterZero(dutyId)) {
            return new ArrayList<>();
        }

        DepartmentDutyDistributionSearchParams searchParams = new DepartmentDutyDistributionSearchParams();
        searchParams.setDutyId(dutyId);

        return find(searchParams).stream().map(DepartmentDutyDistribution::getDepartmentId)
                .collect(Collectors.toList());
    }

    /**
     * 添加记录
     *
     * @param list
     *         待添加记录
     */
    void save(Collection<DepartmentDutyDistribution> list);

    /**
     * 删除记录
     *
     * @param searchParams
     *         搜索参数
     */
    void delete(DepartmentDutyDistributionSearchParams searchParams);

    /**
     * 删除记录
     *
     * @param departmentId
     *         部门ID
     */
    default void deleteByDepartmentId(Long departmentId) {
        if (!NumberHelper.greaterZero(departmentId)) {
            return;
        }

        DepartmentDutyDistributionSearchParams searchParams = new DepartmentDutyDistributionSearchParams();
        searchParams.setDepartmentId(departmentId);

        delete(searchParams);
    }

    /**
     * 删除记录
     *
     * @param dutyId
     *         职务ID
     */
    default void deleteByDutyId(Long dutyId) {
        if (!NumberHelper.greaterZero(dutyId)) {
            return;
        }

        DepartmentDutyDistributionSearchParams searchParams = new DepartmentDutyDistributionSearchParams();
        searchParams.setDutyId(dutyId);

        delete(searchParams);
    }
}
