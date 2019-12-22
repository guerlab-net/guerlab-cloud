package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckResponse;
import net.guerlab.smart.platform.user.service.entity.Position;
import net.guerlab.smart.platform.user.service.service.DutyPermissionService;
import net.guerlab.smart.platform.user.service.service.PermissionCheckService;
import net.guerlab.smart.platform.user.service.service.PositionService;
import net.guerlab.smart.platform.user.service.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限检查服务实现
 *
 * @author guer
 */
@Service
public class PermissionCheckServiceImpl implements PermissionCheckService {

    private UserService userService;

    private PositionService positionService;

    private DutyPermissionService dutyPermissionService;

    @Override
    public PermissionCheckResponse acceptByPermissionKeys(Long userId, Collection<String> permissionKeys) {
        PermissionCheckResponse response = new PermissionCheckResponse();

        if (!NumberHelper.greaterZero(userId)) {
            return response;
        }

        if (userService.isAdmin(userId)) {
            response.setAccept(true);
            return response;
        }

        Collection<String> keys = permissionKeys.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());

        if (CollectionUtil.isBlank(keys)) {
            return response;
        }

        Collection<String> userPermissionKeys = getPermissionKeysByUserId(userId);

        if (CollectionUtil.isBlank(userPermissionKeys)) {
            response.setNotHas(keys);
            return response;
        }

        Map<Boolean, List<String>> hasPermissionKeys = keys.stream()
                .collect(Collectors.groupingBy(userPermissionKeys::contains));

        response.setHas(hasPermissionKeys.getOrDefault(true, Collections.emptyList()));
        response.setNotHas(hasPermissionKeys.getOrDefault(false, Collections.emptyList()));
        response.setAccept(response.getNotHas().isEmpty());

        return response;
    }

    private Collection<String> getPermissionKeysByUserId(Long userId) {
        Collection<Position> positions = positionService.findByUserId(userId);

        return dutyPermissionService.findPermissionKeyList(positions);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    @Autowired
    public void setDutyPermissionService(DutyPermissionService dutyPermissionService) {
        this.dutyPermissionService = dutyPermissionService;
    }
}
