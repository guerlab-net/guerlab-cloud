package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.commons.util.TreeUtils;
import net.guerlab.smart.platform.commons.util.UpdateUtils;
import net.guerlab.smart.platform.server.controller.BaseController;
import net.guerlab.smart.platform.user.core.domain.DepartmentDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.service.entity.Department;
import net.guerlab.smart.platform.user.service.entity.DepartmentPositionDistribution;
import net.guerlab.smart.platform.user.service.service.DepartmentPositionDistributionService;
import net.guerlab.smart.platform.user.service.service.DepartmentService;
import net.guerlab.smart.platform.user.web.domain.ManagerSetterDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 部门
 *
 * @author guer
 */
@Api(tags = "部门")
@RestController("/user/department")
@RequestMapping("/user/department")
public class DepartmentController
        extends BaseController<DepartmentDTO, Department, DepartmentService, DepartmentSearchParams, Long> {

    private DepartmentPositionDistributionService distributionService;

    @Override
    public DepartmentDTO update(@ApiParam(value = "id", required = true) @PathVariable Long id,
            @ApiParam(value = "部门信息", required = true) @RequestBody DepartmentDTO dto) {
        Department department = findOne0(id);

        String departmentName = dto.getDepartmentName();
        String nowDepartmentName = department.getDepartmentName();
        Long parentId = dto.getParentId();
        Long nowParentId = department.getParentId();

        BeanUtils.copyProperties(dto, department);

        department.setDepartmentId(id);
        department.setDepartmentName(UpdateUtils.getUpdateValue(departmentName, nowDepartmentName));
        department.setParentId(UpdateUtils.getUpdateValue(parentId, nowParentId));

        service.updateSelectiveById(department);

        return findOne0(id).toDTO();
    }

    @Override
    protected ApplicationException nullPointException() {
        return new DepartmentInvalidException();
    }

    @ApiOperation("获取树状部门结构")
    @GetMapping("/tree")
    public Collection<DepartmentDTO> tree() {
        return TreeUtils.tree(findAll(null));
    }

    @ApiOperation("设置主管领导")
    @PostMapping("/directorUser")
    public void setDirectorUser(@ApiParam(value = "部门管理设置", required = true) @RequestBody ManagerSetterDTO setter) {
        service.setDirectorUser(setter.getDepartmentId(), setter.getUserId());
    }

    @ApiOperation("设置分管领导")
    @PostMapping("/chargeUser")
    public void setChargeUser(@ApiParam(value = "部门管理设置", required = true) @RequestBody ManagerSetterDTO setter) {
        service.setChargeUser(setter.getDepartmentId(), setter.getUserId());
    }

    @ApiOperation("删除主管领导")
    @DeleteMapping("/directorUser/{departmentId}")
    public void removeDirectorUser(@ApiParam(value = "部门id", required = true) @PathVariable Long departmentId) {
        service.removeDirectorUser(departmentId);
    }

    @ApiOperation("删除分管领导")
    @DeleteMapping("/chargeUser/{departmentId}")
    public void removeChargeUser(@ApiParam(value = "部门id", required = true) @PathVariable Long departmentId) {
        service.removeChargeUser(departmentId);
    }

    @ApiOperation("查询部门已分配的职位ID")
    @GetMapping("/distribution/{departmentId}")
    public Collection<Long> getDistribution(
            @ApiParam(value = "部门id", required = true) @PathVariable Long departmentId) {
        return distributionService.findPositionIdByDepartmentId(departmentId);
    }

    @ApiOperation("设置部门可分配的职位")
    @PostMapping("/distribution/{departmentId}")
    @Transactional(rollbackFor = Exception.class)
    public void setDistribution(@ApiParam(value = "部门id", required = true) @PathVariable Long departmentId,
            @ApiParam(value = "职位ID集合", required = true) @RequestBody Collection<Long> positionIds) {
        distributionService.deleteByDepartmentId(departmentId);

        if (CollectionUtil.isEmpty(positionIds)) {
            return;
        }

        Collection<DepartmentPositionDistribution> list = positionIds.stream()
                .map(positionId -> new DepartmentPositionDistribution(departmentId, positionId))
                .collect(Collectors.toList());

        distributionService.save(list);
    }

    @Autowired
    public void setDistributionService(DepartmentPositionDistributionService distributionService) {
        this.distributionService = distributionService;
    }
}
