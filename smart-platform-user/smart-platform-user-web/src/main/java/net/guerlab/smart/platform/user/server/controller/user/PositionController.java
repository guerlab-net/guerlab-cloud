package net.guerlab.smart.platform.user.server.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.server.controller.BaseController;
import net.guerlab.smart.platform.user.core.domain.PositionDTO;
import net.guerlab.smart.platform.user.core.exception.PositionInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.server.entity.DepartmentPositionDistribution;
import net.guerlab.smart.platform.user.server.entity.Position;
import net.guerlab.smart.platform.user.server.service.DepartmentPositionDistributionService;
import net.guerlab.smart.platform.user.server.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 职位
 *
 * @author guer
 */
@Api(tags = "职位")
@RestController("/user/position")
@RequestMapping("/user/position")
public class PositionController
        extends BaseController<PositionDTO, Position, PositionService, PositionSearchParams, Long> {

    private DepartmentPositionDistributionService distributionService;

    @Override
    public void copyProperties(PositionDTO dto, Position entity, Long id) {
        super.copyProperties(dto, entity, id);
        entity.setPositionId(id);
    }

    @Override
    protected ApplicationException nullPointException() {
        return new PositionInvalidException();
    }

    @ApiOperation("查询可分配该职位的部门")
    @GetMapping("/distribution/{positionId}")
    public Collection<Long> getDistribution(@ApiParam(value = "职位ID", required = true) @PathVariable Long positionId) {
        return distributionService.findDepartmentIdByPositionId(positionId);
    }

    @ApiOperation("设置可分配该职位的部门")
    @PostMapping("/distribution/{positionId}")
    @Transactional(rollbackFor = Exception.class)
    public void setDistribution(@ApiParam(value = "职位ID", required = true) @PathVariable Long positionId,
            @ApiParam(value = "部门ID集合", required = true) @RequestBody Collection<Long> departmentIds) {
        distributionService.deleteByPositionId(positionId);

        if (CollectionUtil.isEmpty(departmentIds)) {
            return;
        }

        Collection<DepartmentPositionDistribution> list = departmentIds.stream()
                .map(departmentId -> new DepartmentPositionDistribution(departmentId, positionId))
                .collect(Collectors.toList());

        distributionService.save(list);
    }

    @Autowired
    public void setDistributionService(DepartmentPositionDistributionService distributionService) {
        this.distributionService = distributionService;
    }
}
