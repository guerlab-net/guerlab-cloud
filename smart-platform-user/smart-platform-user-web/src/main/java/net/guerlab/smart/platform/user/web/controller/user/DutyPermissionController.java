package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.DutyPermissionDTO;
import net.guerlab.smart.platform.user.core.searchparams.DutyPermissionSearchParams;
import net.guerlab.smart.platform.user.service.service.DutyPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职务权限
 *
 * @author guer
 */
@Api(tags = "职务权限")
@RestController("/user/dutyPermission")
@RequestMapping("/user/dutyPermission")
public class DutyPermissionController {

    private DutyPermissionService service;

    @ApiOperation("查询")
    @GetMapping("/{departmentId}/{dutyId}")
    public List<DutyPermissionDTO> findList(@ApiParam(value = "部门ID", required = true) @PathVariable Long departmentId,
            @ApiParam(value = "职务ID", required = true) @PathVariable Long dutyId) {
        DutyPermissionSearchParams searchParams = new DutyPermissionSearchParams();
        searchParams.setDepartmentId(departmentId);
        searchParams.setDutyId(dutyId);

        return BeanConvertUtils.toList(service.findList(searchParams));
    }

    @ApiOperation("保存")
    @PostMapping("/{departmentId}/{dutyId}")
    public void save(@ApiParam(value = "部门ID", required = true) @PathVariable Long departmentId,
            @ApiParam(value = "职务ID", required = true) @PathVariable Long dutyId,
            @ApiParam(value = "权限关键字列表", required = true) @RequestBody List<String> keys) {
        service.save(departmentId, dutyId, keys);
    }

    @Autowired
    public void setService(DutyPermissionService service) {
        this.service = service;
    }
}
