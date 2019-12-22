package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.Constants;
import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.user.core.searchparams.DutyPermissionSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.PermissionSearchParams;
import net.guerlab.smart.platform.user.service.entity.DutyPermission;
import net.guerlab.smart.platform.user.service.entity.Permission;
import net.guerlab.smart.platform.user.service.entity.Position;
import net.guerlab.smart.platform.user.service.mapper.DutyPermissionMapper;
import net.guerlab.smart.platform.user.service.service.DutyPermissionService;
import net.guerlab.smart.platform.user.service.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 职务权限服务实现
 *
 * @author guer
 */
@Service
public class DutyPermissionServiceImpl extends BaseServiceImpl<DutyPermission, Long, DutyPermissionMapper>
        implements DutyPermissionService {

    private static final String ALL_DEPARTMENT_POSITION = "0:0";

    private PermissionService permissionService;

    private static Set<String> getKeys(Collection<Position> list) {
        Set<String> departmentDuties = CollectionUtil.toSet(list, DutyPermissionServiceImpl::getDepartmentDuty);
        Set<String> departmentIds = CollectionUtil.toSet(list, DutyPermissionServiceImpl::getDepartment);
        Set<String> dutyIds = CollectionUtil.toSet(list, DutyPermissionServiceImpl::getDuty);

        Set<String> keys = new HashSet<>(departmentDuties.size() + departmentIds.size() + dutyIds.size() + 1);
        keys.add(ALL_DEPARTMENT_POSITION);
        keys.addAll(departmentDuties);
        keys.addAll(departmentIds);
        keys.addAll(dutyIds);

        return keys;
    }

    private static String getDepartmentDuty(Position position) {
        return format(position.getDepartmentId(), position.getDutyId());
    }

    private static String getDepartment(Position position) {
        return format(position.getDepartmentId(), Constants.EMPTY_ID);
    }

    private static String getDuty(Position position) {
        return format(Constants.EMPTY_ID, position.getDutyId());
    }

    private static String format(Long departmentId, Long dutyId) {
        return getValue(departmentId) + ":" + getValue(dutyId);
    }

    private static Long getValue(Long value) {
        return NumberHelper.greaterZero(value) ? value : Constants.EMPTY_ID;
    }

    private static DutyPermission saveBeforeHandler(DutyPermission dutyPermission) {
        if (StringUtils.isBlank(dutyPermission.getPermissionKey()) || !NumberHelper
                .allGreaterOrEqualZero(dutyPermission.getDepartmentId(), dutyPermission.getDutyId())) {
            return null;
        }

        dutyPermission.setDepartmentDuty(format(dutyPermission.getDepartmentId(), dutyPermission.getDutyId()));
        dutyPermission.setPermissionKey(dutyPermission.getPermissionKey());

        return dutyPermission;
    }

    @Override
    public Class<DutyPermission> getEntityClass() {
        return DutyPermission.class;
    }

    @Override
    public Collection<DutyPermission> findList(DutyPermissionSearchParams searchParams) {
        return mapper.selectByExample(getExample(searchParams));
    }

    @Override
    public Collection<DutyPermission> findList(Collection<Position> list) {
        if (CollectionUtil.isBlank(list)) {
            return Collections.emptyList();
        }

        DutyPermissionSearchParams searchParams = new DutyPermissionSearchParams();
        searchParams.setDepartmentDuties(getKeys(list));

        return findList(searchParams);
    }

    @Override
    public void save(Long departmentId, Long dutyId, Collection<String> keys) {
        DutyPermissionSearchParams searchParams = new DutyPermissionSearchParams();
        searchParams.setDepartmentId(departmentId);
        searchParams.setDutyId(dutyId);

        delete(searchParams);

        if (CollectionUtil.isEmpty(keys)) {
            return;
        }

        save0(CollectionUtil.toList(keys, key -> {
            DutyPermission dutyPermission = new DutyPermission();
            dutyPermission.setDepartmentId(departmentId);
            dutyPermission.setPermissionKey(StringUtils.trim(key));
            dutyPermission.setDutyId(dutyId);

            return dutyPermission;
        }));
    }

    private void save0(Collection<DutyPermission> list) {
        Collection<DutyPermission> filled = list.stream().map(DutyPermissionServiceImpl::saveBeforeHandler)
                .filter(Objects::nonNull).collect(Collectors.toList());

        if (CollectionUtil.isEmpty(filled)) {
            return;
        }

        Map<String, List<DutyPermission>> keyMap = CollectionUtil.group(filled, DutyPermission::getPermissionKey);

        Collection<Permission> permissions = findPermissionByKeyList(keyMap.keySet());

        List<DutyPermission> saves = new ArrayList<>(filled.size());

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
    public void delete(DutyPermissionSearchParams searchParams) {
        mapper.deleteByExample(getExample(searchParams));
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
