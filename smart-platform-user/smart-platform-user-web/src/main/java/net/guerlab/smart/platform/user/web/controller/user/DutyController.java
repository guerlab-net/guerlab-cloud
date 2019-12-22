package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.server.controller.BaseController;
import net.guerlab.smart.platform.user.core.domain.DutyDTO;
import net.guerlab.smart.platform.user.core.exception.DutyInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DutySearchParams;
import net.guerlab.smart.platform.user.service.entity.DepartmentDutyDistribution;
import net.guerlab.smart.platform.user.service.entity.Duty;
import net.guerlab.smart.platform.user.service.service.DepartmentDutyDistributionService;
import net.guerlab.smart.platform.user.service.service.DutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 职务
 *
 * @author guer
 */
@Api(tags = "职务")
@RestController("/user/duty")
@RequestMapping("/user/duty")
public class DutyController extends BaseController<DutyDTO, Duty, DutyService, DutySearchParams, Long> {

    private DepartmentDutyDistributionService distributionService;

    @Override
    public void copyProperties(DutyDTO dto, Duty entity, Long id) {
        super.copyProperties(dto, entity, id);
        entity.setDutyId(id);
    }

    @Override
    protected ApplicationException nullPointException() {
        return new DutyInvalidException();
    }

    @ApiOperation("查询可分配该职务的部门")
    @GetMapping("/distribution/{dutyId}")
    public Collection<Long> getDistribution(@ApiParam(value = "职务ID", required = true) @PathVariable Long dutyId) {
        return distributionService.findDepartmentIdByDutyId(dutyId);
    }

    @ApiOperation("设置可分配该职务的部门")
    @PostMapping("/distribution/{dutyId}")
    @Transactional(rollbackFor = Exception.class)
    public void setDistribution(@ApiParam(value = "职务ID", required = true) @PathVariable Long dutyId,
            @ApiParam(value = "部门ID集合", required = true) @RequestBody Collection<Long> departmentIds) {
        distributionService.deleteByDutyId(dutyId);

        if (CollectionUtil.isEmpty(departmentIds)) {
            return;
        }

        Collection<DepartmentDutyDistribution> list = departmentIds.stream()
                .map(departmentId -> new DepartmentDutyDistribution(departmentId, dutyId)).collect(Collectors.toList());

        distributionService.save(list);
    }

    @Autowired
    public void setDistributionService(DepartmentDutyDistributionService distributionService) {
        this.distributionService = distributionService;
    }
}
