package net.guerlab.smart.platform.user.service.service;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.exception.UserIdInvalidException;
import net.guerlab.smart.platform.user.core.exception.DepartmentIdInvalidException;
import net.guerlab.smart.platform.user.core.exception.DutyIdInvalidException;
import net.guerlab.smart.platform.user.core.exception.PositionInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.service.entity.Position;
import net.guerlab.web.result.ListObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 职位服务
 *
 * @author guer
 */
public interface PositionService {

    /**
     * 通过搜索参数查询职位信息
     *
     * @param searchParams
     *         搜索参数
     * @return 职位信息
     */
    Position findOne(PositionSearchParams searchParams);

    /**
     * 通过搜索参数判断是否有职位信息
     *
     * @param searchParams
     *         搜索参数
     * @return 是否有职位信息
     */
    default boolean has(PositionSearchParams searchParams) {
        return findOne(searchParams) != null;
    }

    /**
     * 通过搜索参数查询职位信息列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职位信息列表
     */
    Collection<Position> findList(PositionSearchParams searchParams);

    /**
     * 通过搜索参数查询用户ID列表
     *
     * @param searchParams
     *         搜索参数
     * @return 用户ID列表
     */
    default Collection<Long> findUserIdList(PositionSearchParams searchParams) {
        return findList(searchParams).stream().map(Position::getUserId).collect(Collectors.toList());
    }

    /**
     * 通过搜索参数查询部门ID列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门ID列表
     */
    default Collection<Long> findDepartmentIdList(PositionSearchParams searchParams) {
        return findList(searchParams).stream().map(Position::getDepartmentId).collect(Collectors.toList());
    }

    /**
     * 通过搜索参数查询职务ID列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职务ID列表
     */
    default Collection<Long> findDutyIdList(PositionSearchParams searchParams) {
        return findList(searchParams).stream().map(Position::getDutyId).collect(Collectors.toList());
    }

    /**
     * 通过搜索参数查询职位信息列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职位信息列表
     */
    ListObject<Position> findPage(PositionSearchParams searchParams);

    /**
     * 通过用户id查询职位信息列表
     *
     * @param userId
     *         用户id
     * @return 职位信息列表
     */
    default Collection<Position> findByUserId(Long userId) {
        if (!NumberHelper.greaterZero(userId)) {
            return new ArrayList<>();
        }

        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setUserId(userId);

        return findList(searchParams);
    }

    /**
     * 通过部门id查询职位信息列表
     *
     * @param departmentId
     *         部门id
     * @return 职位信息列表
     */
    default Collection<Position> findByDepartmentId(Long departmentId) {
        if (!NumberHelper.greaterZero(departmentId)) {
            return new ArrayList<>();
        }

        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setDepartmentId(departmentId);

        return findList(searchParams);
    }

    /**
     * 保存职位信息
     *
     * @param position
     *         职位信息
     */
    default void save(Position position) {
        if (position == null) {
            throw new PositionInvalidException();
        }

        if (!NumberHelper.greaterZero(position.getDepartmentId())) {
            throw new DepartmentIdInvalidException();
        }

        if (!NumberHelper.greaterZero(position.getDutyId())) {
            throw new DutyIdInvalidException();
        }

        if (!NumberHelper.greaterZero(position.getUserId())) {
            throw new UserIdInvalidException();
        }

        save(Collections.singletonList(position));
    }

    /**
     * 保存职位信息
     *
     * @param userId
     *         用户id
     * @param departmentId
     *         部门ID
     * @param dutyId
     *         职务ID
     */
    default void save(Long userId, Long departmentId, Long dutyId) {
        if (!NumberHelper.allGreaterZero(userId, departmentId, dutyId)) {
            return;
        }

        Position position = new Position();
        position.setUserId(userId);
        position.setDepartmentId(departmentId);
        position.setDutyId(dutyId);

        save(position);
    }

    /**
     * 保存职位信息
     *
     * @param positionList
     *         职位信息列表
     */
    void save(Collection<Position> positionList);

    /**
     * 根据搜索参数删除职位信息
     *
     * @param searchParams
     *         搜索参数
     */
    void delete(PositionSearchParams searchParams);

    /**
     * 删除职位信息
     *
     * @param position
     *         职位信息
     */
    default void delete(Position position) {
        if (position == null) {
            return;
        }

        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setUserId(position.getUserId());
        searchParams.setDepartmentId(position.getDepartmentId());
        searchParams.setDutyId(position.getDutyId());

        delete(searchParams);
    }

    /**
     * 删除职位信息
     *
     * @param userId
     *         用户id
     * @param departmentId
     *         部门ID
     * @param dutyId
     *         职务ID
     */
    default void delete(Long userId, Long departmentId, Long dutyId) {
        if (!NumberHelper.allGreaterZero(userId, departmentId, dutyId)) {
            return;
        }

        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setUserId(userId);
        searchParams.setDepartmentId(departmentId);
        searchParams.setDutyId(dutyId);

        delete(searchParams);
    }
}
