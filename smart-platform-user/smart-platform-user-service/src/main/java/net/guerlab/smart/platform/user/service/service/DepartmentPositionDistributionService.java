package net.guerlab.smart.platform.user.service.service;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentPositionDistributionSearchParams;
import net.guerlab.smart.platform.user.service.entity.DepartmentPositionDistribution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 部门职位分配服务
 *
 * @author guer
 */
public interface DepartmentPositionDistributionService {

    /**
     * 根据搜索参数查询列表
     *
     * @param searchParams
     *         搜索参数
     * @return 分配列表
     */
    Collection<DepartmentPositionDistribution> find(DepartmentPositionDistributionSearchParams searchParams);

    /**
     * 判断是否存在分配记录
     *
     * @param departmentId
     *         部门ID
     * @param positionId
     *         职位ID
     * @return 是否存在分配记录
     */
    default boolean has(Long departmentId, Long positionId) {
        if (!NumberHelper.allGreaterZero(departmentId, positionId)) {
            return false;
        }

        DepartmentPositionDistributionSearchParams searchParams = new DepartmentPositionDistributionSearchParams();
        searchParams.setDepartmentId(departmentId);
        searchParams.setPositionId(positionId);

        return !find(searchParams).isEmpty();
    }

    /**
     * 通过部门ID查询职位ID集合
     *
     * @param departmentId
     *         部门ID
     * @return 职位ID集合
     */
    default Collection<Long> findPositionIdByDepartmentId(Long departmentId) {
        if (!NumberHelper.greaterZero(departmentId)) {
            return new ArrayList<>();
        }

        DepartmentPositionDistributionSearchParams searchParams = new DepartmentPositionDistributionSearchParams();
        searchParams.setDepartmentId(departmentId);

        return find(searchParams).stream().map(DepartmentPositionDistribution::getPositionId)
                .collect(Collectors.toList());
    }

    /**
     * 通过职位ID查询部门ID集合
     *
     * @param positionId
     *         职位ID
     * @return 部门ID集合
     */
    default Collection<Long> findDepartmentIdByPositionId(Long positionId) {
        if (!NumberHelper.greaterZero(positionId)) {
            return new ArrayList<>();
        }

        DepartmentPositionDistributionSearchParams searchParams = new DepartmentPositionDistributionSearchParams();
        searchParams.setPositionId(positionId);

        return find(searchParams).stream().map(DepartmentPositionDistribution::getDepartmentId)
                .collect(Collectors.toList());
    }

    /**
     * 添加记录
     *
     * @param list
     *         待添加记录
     */
    void save(Collection<DepartmentPositionDistribution> list);

    /**
     * 删除记录
     *
     * @param searchParams
     *         搜索参数
     */
    void delete(DepartmentPositionDistributionSearchParams searchParams);

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

        DepartmentPositionDistributionSearchParams searchParams = new DepartmentPositionDistributionSearchParams();
        searchParams.setDepartmentId(departmentId);

        delete(searchParams);
    }

    /**
     * 删除记录
     *
     * @param positionId
     *         职位ID
     */
    default void deleteByPositionId(Long positionId) {
        if (!NumberHelper.greaterZero(positionId)) {
            return;
        }

        DepartmentPositionDistributionSearchParams searchParams = new DepartmentPositionDistributionSearchParams();
        searchParams.setPositionId(positionId);

        delete(searchParams);
    }
}
