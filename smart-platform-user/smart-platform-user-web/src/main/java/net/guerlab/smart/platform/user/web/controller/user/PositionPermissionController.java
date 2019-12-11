package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.PositionPermissionDTO;
import net.guerlab.smart.platform.user.core.searchparams.PositionPermissionSearchParams;
import net.guerlab.smart.platform.user.service.service.PositionPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职位权限
 *
 * @author guer
 */
@Api(tags = "职位权限")
@RestController("/user/positionPermission")
@RequestMapping("/user/positionPermission")
public class PositionPermissionController {

    private PositionPermissionService service;

    @ApiOperation("查询")
    @GetMapping("/{departmentId}/{positionId}")
    public List<PositionPermissionDTO> findList(
            @ApiParam(value = "部门ID", required = true) @PathVariable Long departmentId,
            @ApiParam(value = "职位ID", required = true) @PathVariable Long positionId) {
        PositionPermissionSearchParams searchParams = new PositionPermissionSearchParams();
        searchParams.setDepartmentId(departmentId);
        searchParams.setPositionId(positionId);

        return BeanConvertUtils.toList(service.findList(searchParams));
    }

    @ApiOperation("保存")
    @PostMapping("/{departmentId}/{positionId}")
    public void save(@ApiParam(value = "部门ID", required = true) @PathVariable Long departmentId,
            @ApiParam(value = "职位ID", required = true) @PathVariable Long positionId,
            @ApiParam(value = "权限关键字列表", required = true) @RequestBody List<String> keys) {
        service.save(departmentId, positionId, keys);
    }

    @Autowired
    public void setService(PositionPermissionService service) {
        this.service = service;
    }
}
