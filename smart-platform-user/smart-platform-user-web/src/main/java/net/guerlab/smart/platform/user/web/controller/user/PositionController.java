package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.exception.UserIdInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.UserAuthConstants;
import net.guerlab.smart.platform.user.core.domain.DepartmentDTO;
import net.guerlab.smart.platform.user.core.domain.DutyDTO;
import net.guerlab.smart.platform.user.core.domain.PositionDTO;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentIdInvalidException;
import net.guerlab.smart.platform.user.core.exception.DepartmentNotHasDutyDistributionException;
import net.guerlab.smart.platform.user.core.exception.SystemDutyCannotOperationException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.DutySearchParams;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.service.entity.Position;
import net.guerlab.smart.platform.user.service.service.*;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 职位
 *
 * @author guer
 */
@Api(tags = "职位")
@RestController("/user/position")
@RequestMapping("/user/position")
public class PositionController {

    private PositionService positionService;

    private UserService userService;

    private DepartmentService departmentService;

    private DutyService dutyService;

    private DepartmentDutyDistributionService distributionService;

    @ApiOperation("查询列表")
    @GetMapping
    public ListObject<PositionDTO> findList(PositionSearchParams searchParams) {
        ListObject<PositionDTO> result = BeanConvertUtils.toListObject(positionService.findPage(searchParams));

        set(result.getList());

        return result;
    }

    @ApiOperation("查询全部")
    @GetMapping("/all")
    public List<PositionDTO> findAll(PositionSearchParams searchParams) {
        List<PositionDTO> list = BeanConvertUtils.toList(positionService.findList(searchParams));

        set(list);

        return list;
    }

    @ApiOperation("保存")
    @PostMapping("/{userId}/{departmentId}/{dutyId}")
    @Transactional(rollbackFor = Exception.class)
    public void save(@ApiParam(value = "用户id", required = true) @PathVariable Long userId,
            @ApiParam(value = "部门id", required = true) @PathVariable Long departmentId,
            @ApiParam(value = "职务id", required = true) @PathVariable Long dutyId) {
        paramsCheck(userId, departmentId, dutyId);

        if (!distributionService.has(departmentId, dutyId)) {
            throw new DepartmentNotHasDutyDistributionException();
        }

        Position position = new Position();
        position.setDepartmentId(departmentId);
        position.setDutyId(dutyId);
        position.setUserId(userId);

        positionService.save(position);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{userId}/{departmentId}/{dutyId}")
    public void delete(@ApiParam(value = "用户id", required = true) @PathVariable Long userId,
            @ApiParam(value = "部门id", required = true) @PathVariable Long departmentId,
            @ApiParam(value = "职务id", required = true) @PathVariable Long dutyId) {
        paramsCheck(userId, departmentId, dutyId);

        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setUserId(userId);
        searchParams.setDepartmentId(departmentId);
        searchParams.setDutyId(dutyId);

        positionService.delete(searchParams);
    }

    private void paramsCheck(Long userId, Long departmentId, Long dutyId) {
        if (!NumberHelper.greaterZero(userId)) {
            throw new UserIdInvalidException();
        }
        if (!NumberHelper.greaterZero(departmentId)) {
            throw new DepartmentIdInvalidException();
        }
        if (dutyId == null || dutyId < UserAuthConstants.SYSTEM_POSITION_ID_RANGE) {
            throw new SystemDutyCannotOperationException();
        }
    }

    private void set(Collection<PositionDTO> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }

        setUser(list);
        setDepartment(list);
        setDuty(list);
    }

    private void setUser(Collection<PositionDTO> list) {
        Map<Long, List<PositionDTO>> maps = CollectionUtil.group(list, PositionDTO::getUserId);

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUserIds(maps.keySet());

        userService.selectAll(searchParams).forEach(user -> {
            UserDTO dto = user.toDTO();

            List<PositionDTO> office = maps.get(user.getUserId());

            if (office != null) {
                office.forEach(o -> o.setUser(dto));
            }
        });
    }

    private void setDepartment(Collection<PositionDTO> list) {
        Map<Long, List<PositionDTO>> maps = CollectionUtil.group(list, PositionDTO::getDepartmentId);

        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDepartmentIds(maps.keySet());

        departmentService.selectAll(searchParams).forEach(department -> {
            DepartmentDTO dto = department.toDTO();

            List<PositionDTO> office = maps.get(department.getDepartmentId());

            if (office != null) {
                office.forEach(o -> o.setDepartment(dto));
            }
        });
    }

    private void setDuty(Collection<PositionDTO> list) {
        Map<Long, List<PositionDTO>> maps = CollectionUtil.group(list, PositionDTO::getDutyId);

        DutySearchParams searchParams = new DutySearchParams();
        searchParams.setDutyIds(maps.keySet());

        dutyService.selectAll(searchParams).forEach(duty -> {
            DutyDTO dto = duty.toDTO();

            List<PositionDTO> office = maps.get(duty.getDutyId());

            if (office != null) {
                office.forEach(o -> o.setDuty(dto));
            }
        });
    }

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setDutyService(DutyService dutyService) {
        this.dutyService = dutyService;
    }

    @Autowired
    public void setDistributionService(DepartmentDutyDistributionService distributionService) {
        this.distributionService = distributionService;
    }
}
