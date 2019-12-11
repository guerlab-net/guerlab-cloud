package net.guerlab.smart.platform.user.service.service;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.exception.UserIdInvalidException;
import net.guerlab.smart.platform.user.core.exception.DepartmentIdInvalidException;
import net.guerlab.smart.platform.user.core.exception.PositionIdInvalidException;
import net.guerlab.smart.platform.user.core.exception.TakeOfficeInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.TakeOfficeSearchParams;
import net.guerlab.smart.platform.user.service.entity.TakeOffice;
import net.guerlab.web.result.ListObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 任职服务
 *
 * @author guer
 */
public interface TakeOfficeService {

    /**
     * 通过搜索参数查询任职信息
     *
     * @param searchParams
     *         搜索参数
     * @return 任职信息
     */
    TakeOffice findOne(TakeOfficeSearchParams searchParams);

    /**
     * 通过搜索参数判断是否有任职信息
     *
     * @param searchParams
     *         搜索参数
     * @return 是否有任职信息
     */
    default boolean has(TakeOfficeSearchParams searchParams) {
        return findOne(searchParams) != null;
    }

    /**
     * 通过搜索参数查询任职信息列表
     *
     * @param searchParams
     *         搜索参数
     * @return 任职信息列表
     */
    Collection<TakeOffice> findList(TakeOfficeSearchParams searchParams);

    /**
     * 通过搜索参数查询用户ID列表
     *
     * @param searchParams
     *         搜索参数
     * @return 用户ID列表
     */
    default Collection<Long> findUserIdList(TakeOfficeSearchParams searchParams) {
        return findList(searchParams).stream().map(TakeOffice::getUserId).collect(Collectors.toList());
    }

    /**
     * 通过搜索参数查询部门ID列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门ID列表
     */
    default Collection<Long> findDepartmentIdList(TakeOfficeSearchParams searchParams) {
        return findList(searchParams).stream().map(TakeOffice::getDepartmentId).collect(Collectors.toList());
    }

    /**
     * 通过搜索参数查询职位ID列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职位ID列表
     */
    default Collection<Long> findPositionIdList(TakeOfficeSearchParams searchParams) {
        return findList(searchParams).stream().map(TakeOffice::getPositionId).collect(Collectors.toList());
    }

    /**
     * 通过搜索参数查询任职信息列表
     *
     * @param searchParams
     *         搜索参数
     * @return 任职信息列表
     */
    ListObject<TakeOffice> findPage(TakeOfficeSearchParams searchParams);

    /**
     * 通过用户id查询任职信息列表
     *
     * @param userId
     *         用户id
     * @return 任职信息列表
     */
    default Collection<TakeOffice> findByUserId(Long userId) {
        if (!NumberHelper.greaterZero(userId)) {
            return new ArrayList<>();
        }

        TakeOfficeSearchParams searchParams = new TakeOfficeSearchParams();
        searchParams.setUserId(userId);

        return findList(searchParams);
    }

    /**
     * 通过部门id查询任职信息列表
     *
     * @param departmentId
     *         部门id
     * @return 任职信息列表
     */
    default Collection<TakeOffice> findByDepartmentId(Long departmentId) {
        if (!NumberHelper.greaterZero(departmentId)) {
            return new ArrayList<>();
        }

        TakeOfficeSearchParams searchParams = new TakeOfficeSearchParams();
        searchParams.setDepartmentId(departmentId);

        return findList(searchParams);
    }

    /**
     * 保存任职信息
     *
     * @param takeOffice
     *         任职信息
     */
    default void save(TakeOffice takeOffice) {
        if (takeOffice == null) {
            throw new TakeOfficeInvalidException();
        }

        if (!NumberHelper.greaterZero(takeOffice.getDepartmentId())) {
            throw new DepartmentIdInvalidException();
        }

        if (!NumberHelper.greaterZero(takeOffice.getPositionId())) {
            throw new PositionIdInvalidException();
        }

        if (!NumberHelper.greaterZero(takeOffice.getUserId())) {
            throw new UserIdInvalidException();
        }

        save(Collections.singletonList(takeOffice));
    }

    /**
     * 保存任职信息
     *
     * @param userId
     *         用户id
     * @param departmentId
     *         部门ID
     * @param positionId
     *         职位ID
     */
    default void save(Long userId, Long departmentId, Long positionId) {
        if (!NumberHelper.allGreaterZero(userId, departmentId, positionId)) {
            return;
        }

        TakeOffice takeOffice = new TakeOffice();
        takeOffice.setUserId(userId);
        takeOffice.setDepartmentId(departmentId);
        takeOffice.setPositionId(positionId);

        save(takeOffice);
    }

    /**
     * 保存任职信息
     *
     * @param takeOfficeList
     *         任职信息列表
     */
    void save(Collection<TakeOffice> takeOfficeList);

    /**
     * 根据搜索参数删除任职信息
     *
     * @param searchParams
     *         搜索参数
     */
    void delete(TakeOfficeSearchParams searchParams);

    /**
     * 删除任职信息
     *
     * @param takeOffice
     *         任职信息
     */
    default void delete(TakeOffice takeOffice) {
        if (takeOffice == null) {
            return;
        }

        TakeOfficeSearchParams searchParams = new TakeOfficeSearchParams();
        searchParams.setUserId(takeOffice.getUserId());
        searchParams.setDepartmentId(takeOffice.getDepartmentId());
        searchParams.setPositionId(takeOffice.getPositionId());

        delete(searchParams);
    }

    /**
     * 删除任职信息
     *
     * @param userId
     *         用户id
     * @param departmentId
     *         部门ID
     * @param positionId
     *         职位ID
     */
    default void delete(Long userId, Long departmentId, Long positionId) {
        if (!NumberHelper.allGreaterZero(userId, departmentId, positionId)) {
            return;
        }

        TakeOfficeSearchParams searchParams = new TakeOfficeSearchParams();
        searchParams.setUserId(userId);
        searchParams.setDepartmentId(departmentId);
        searchParams.setPositionId(positionId);

        delete(searchParams);
    }
}
