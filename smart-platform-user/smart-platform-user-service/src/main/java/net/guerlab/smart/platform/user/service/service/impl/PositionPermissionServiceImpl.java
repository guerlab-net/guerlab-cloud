package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.Constants;
import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.user.core.searchparams.PermissionSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.PositionPermissionSearchParams;
import net.guerlab.smart.platform.user.service.entity.Permission;
import net.guerlab.smart.platform.user.service.entity.PositionPermission;
import net.guerlab.smart.platform.user.service.entity.TakeOffice;
import net.guerlab.smart.platform.user.service.mapper.PositionPermissionMapper;
import net.guerlab.smart.platform.user.service.service.PermissionService;
import net.guerlab.smart.platform.user.service.service.PositionPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 职位权限服务实现
 *
 * @author guer
 */
@Service
public class PositionPermissionServiceImpl extends BaseServiceImpl<PositionPermission, Long, PositionPermissionMapper>
        implements PositionPermissionService {

    private static final String ALL_DEPARTMENT_POSITION = "0:0";

    private PermissionService permissionService;

    private static Set<String> getKeys(Collection<TakeOffice> list) {
        Set<String> departmentPositions = CollectionUtil
                .toSet(list, PositionPermissionServiceImpl::getDepartmentPosition);
        Set<String> departmentIds = CollectionUtil.toSet(list, PositionPermissionServiceImpl::getDepartment);
        Set<String> positionIds = CollectionUtil.toSet(list, PositionPermissionServiceImpl::getPosition);

        Set<String> keys = new HashSet<>(departmentPositions.size() + departmentIds.size() + positionIds.size() + 1);
        keys.add(ALL_DEPARTMENT_POSITION);
        keys.addAll(departmentPositions);
        keys.addAll(departmentIds);
        keys.addAll(positionIds);

        return keys;
    }

    private static String getDepartmentPosition(TakeOffice takeOffice) {
        return format(takeOffice.getDepartmentId(), takeOffice.getPositionId());
    }

    private static String getDepartment(TakeOffice takeOffice) {
        return format(takeOffice.getDepartmentId(), Constants.EMPTY_ID);
    }

    private static String getPosition(TakeOffice takeOffice) {
        return format(Constants.EMPTY_ID, takeOffice.getPositionId());
    }

    private static String format(Long departmentId, Long positionId) {
        return getValue(departmentId) + ":" + getValue(positionId);
    }

    private static Long getValue(Long value) {
        return NumberHelper.greaterZero(value) ? value : Constants.EMPTY_ID;
    }

    private static PositionPermission saveBeforeHandler(PositionPermission positionPermission) {
        if (StringUtils.isBlank(positionPermission.getPermissionKey()) || !NumberHelper
                .allGreaterOrEqualZero(positionPermission.getDepartmentId(), positionPermission.getPositionId())) {
            return null;
        }

        positionPermission.setDepartmentPosition(
                format(positionPermission.getDepartmentId(), positionPermission.getPositionId()));
        positionPermission.setPermissionKey(positionPermission.getPermissionKey());

        return positionPermission;
    }

    @Override
    public Class<PositionPermission> getEntityClass() {
        return PositionPermission.class;
    }

    @Override
    public Collection<PositionPermission> findList(PositionPermissionSearchParams searchParams) {
        return mapper.selectByExample(getExample(searchParams));
    }

    @Override
    public Collection<PositionPermission> findList(Collection<TakeOffice> list) {
        if (CollectionUtil.isBlank(list)) {
            return Collections.emptyList();
        }

        PositionPermissionSearchParams searchParams = new PositionPermissionSearchParams();
        searchParams.setDepartmentPositions(getKeys(list));

        return findList(searchParams);
    }

    @Override
    public void save(Long departmentId, Long positionId, Collection<String> keys) {
        PositionPermissionSearchParams searchParams = new PositionPermissionSearchParams();
        searchParams.setDepartmentId(departmentId);
        searchParams.setPositionId(positionId);

        delete(searchParams);

        if (CollectionUtil.isEmpty(keys)) {
            return;
        }

        save0(CollectionUtil.toList(keys, key -> {
            PositionPermission positionPermission = new PositionPermission();
            positionPermission.setDepartmentId(departmentId);
            positionPermission.setPermissionKey(StringUtils.trim(key));
            positionPermission.setPositionId(positionId);

            return positionPermission;
        }));
    }

    private void save0(Collection<PositionPermission> list) {
        Collection<PositionPermission> filled = list.stream().map(PositionPermissionServiceImpl::saveBeforeHandler)
                .filter(Objects::nonNull).collect(Collectors.toList());

        if (CollectionUtil.isEmpty(filled)) {
            return;
        }

        Map<String, List<PositionPermission>> keyMap = CollectionUtil
                .group(filled, PositionPermission::getPermissionKey);

        Collection<Permission> permissions = findPermissionByKeyList(keyMap.keySet());

        List<PositionPermission> saves = new ArrayList<>(filled.size());

        permissions.stream().map(permission -> keyMap.get(permission.getPermissionKey()))
                .filter(CollectionUtil::isNotEmpty).forEach(saves::addAll);

        if (CollectionUtil.isNotEmpty(saves)) {
            mapper.replaceInsertList(saves);
        }
    }

    private Collection<Permission> findPermissionByKeyList(Collection<String> permissionKeyList) {
        PermissionSearchParams searchParams = new PermissionSearchParams();
        searchParams.setPermissionKeys(permissionKeyList);

        return permissionService.selectAll(searchParams);
    }

    @Override
    public void delete(PositionPermissionSearchParams searchParams) {
        mapper.deleteByExample(getExample(searchParams));
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
