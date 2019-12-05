package net.guerlab.smart.platform.user.server.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.exception.UserIdInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.UserAuthConstants;
import net.guerlab.smart.platform.user.core.domain.DepartmentDTO;
import net.guerlab.smart.platform.user.core.domain.PositionDTO;
import net.guerlab.smart.platform.user.core.domain.TakeOfficeDTO;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentIdInvalidException;
import net.guerlab.smart.platform.user.core.exception.DepartmentNotHasPositionDistributionException;
import net.guerlab.smart.platform.user.core.exception.SystemPositionCannotOperationException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.TakeOfficeSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.server.entity.TakeOffice;
import net.guerlab.smart.platform.user.server.service.*;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 任职信息
 *
 * @author guer
 */
@Api(tags = "任职信息")
@RestController("/user/takeOffice")
@RequestMapping("/user/takeOffice")
public class TakeOfficeController {

    private TakeOfficeService takeOfficeService;

    private UserService userService;

    private DepartmentService departmentService;

    private PositionService positionService;

    private DepartmentPositionDistributionService distributionService;

    @ApiOperation("查询列表")
    @GetMapping
    public ListObject<TakeOfficeDTO> findList(TakeOfficeSearchParams searchParams) {
        ListObject<TakeOfficeDTO> result = BeanConvertUtils.toListObject(takeOfficeService.findPage(searchParams));

        set(result.getList());

        return result;
    }

    @ApiOperation("查询全部")
    @GetMapping("/all")
    public List<TakeOfficeDTO> findAll(TakeOfficeSearchParams searchParams) {
        List<TakeOfficeDTO> list = BeanConvertUtils.toList(takeOfficeService.findList(searchParams));

        set(list);

        return list;
    }

    @ApiOperation("保存")
    @PostMapping("/{userId}/{departmentId}/{positionId}")
    @Transactional(rollbackFor = Exception.class)
    public void save(@ApiParam(value = "用户id", required = true) @PathVariable Long userId,
            @ApiParam(value = "部门id", required = true) @PathVariable Long departmentId,
            @ApiParam(value = "职位id", required = true) @PathVariable Long positionId) {
        paramsCheck(userId, departmentId, positionId);

        if (!distributionService.has(departmentId, positionId)) {
            throw new DepartmentNotHasPositionDistributionException();
        }

        TakeOffice takeOffice = new TakeOffice();
        takeOffice.setDepartmentId(departmentId);
        takeOffice.setPositionId(positionId);
        takeOffice.setUserId(userId);

        takeOfficeService.save(takeOffice);
    }

    @ApiOperation("删除")
    @DeleteMapping("/{userId}/{departmentId}/{positionId}")
    public void delete(@ApiParam(value = "用户id", required = true) @PathVariable Long userId,
            @ApiParam(value = "部门id", required = true) @PathVariable Long departmentId,
            @ApiParam(value = "职位id", required = true) @PathVariable Long positionId) {
        paramsCheck(userId, departmentId, positionId);

        TakeOfficeSearchParams searchParams = new TakeOfficeSearchParams();
        searchParams.setUserId(userId);
        searchParams.setDepartmentId(departmentId);
        searchParams.setPositionId(positionId);

        takeOfficeService.delete(searchParams);
    }

    private void paramsCheck(Long userId, Long departmentId, Long positionId) {
        if (!NumberHelper.greaterZero(userId)) {
            throw new UserIdInvalidException();
        }
        if (!NumberHelper.greaterZero(departmentId)) {
            throw new DepartmentIdInvalidException();
        }
        if (positionId == null || positionId < UserAuthConstants.SYSTEM_POSITION_ID_RANGE) {
            throw new SystemPositionCannotOperationException();
        }
    }

    private void set(Collection<TakeOfficeDTO> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }

        setUser(list);
        setDepartment(list);
        setPosition(list);
    }

    private void setUser(Collection<TakeOfficeDTO> list) {
        Map<Long, List<TakeOfficeDTO>> maps = CollectionUtil.group(list, TakeOfficeDTO::getUserId);

        UserSearchParams searchParams = new UserSearchParams();
        searchParams.setUserIds(maps.keySet());

        userService.selectAll(searchParams).forEach(user -> {
            UserDTO dto = user.toDTO();

            List<TakeOfficeDTO> office = maps.get(user.getUserId());

            if (office != null) {
                office.forEach(o -> o.setUser(dto));
            }
        });
    }

    private void setDepartment(Collection<TakeOfficeDTO> list) {
        Map<Long, List<TakeOfficeDTO>> maps = CollectionUtil.group(list, TakeOfficeDTO::getDepartmentId);

        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDepartmentIds(maps.keySet());

        departmentService.selectAll(searchParams).forEach(department -> {
            DepartmentDTO dto = department.toDTO();

            List<TakeOfficeDTO> office = maps.get(department.getDepartmentId());

            if (office != null) {
                office.forEach(o -> o.setDepartment(dto));
            }
        });
    }

    private void setPosition(Collection<TakeOfficeDTO> list) {
        Map<Long, List<TakeOfficeDTO>> maps = CollectionUtil.group(list, TakeOfficeDTO::getPositionId);

        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setPositionIds(maps.keySet());

        positionService.selectAll(searchParams).forEach(position -> {
            PositionDTO dto = position.toDTO();

            List<TakeOfficeDTO> office = maps.get(position.getPositionId());

            if (office != null) {
                office.forEach(o -> o.setPosition(dto));
            }
        });
    }

    @Autowired
    public void setTakeOfficeService(TakeOfficeService takeOfficeService) {
        this.takeOfficeService = takeOfficeService;
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
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    @Autowired
    public void setDistributionService(DepartmentPositionDistributionService distributionService) {
        this.distributionService = distributionService;
    }
}
