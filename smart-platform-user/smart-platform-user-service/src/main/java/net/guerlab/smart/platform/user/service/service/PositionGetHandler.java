package net.guerlab.smart.platform.user.service.service;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.PositionDataDTO;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.DutySearchParams;
import net.guerlab.smart.platform.user.service.entity.Department;
import net.guerlab.smart.platform.user.service.entity.Duty;
import net.guerlab.smart.platform.user.service.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 职位信息列表获取处理
 *
 * @author guer
 */
@Component
public class PositionGetHandler {

    private PositionService positionService;

    private DepartmentService departmentService;

    private DutyService dutyService;

    /**
     * 获取职位信息列表
     *
     * @param userId
     *         用户ID
     * @return 职位信息列表
     */
    public List<PositionDataDTO> getPosition(Long userId) {
        Collection<Position> positions = positionService.findByUserId(userId);

        if (CollectionUtil.isEmpty(positions)) {
            return Collections.emptyList();
        }

        List<PositionDataDTO> positionDTO = BeanConvertUtils.toList(positions, PositionDataDTO.class);

        Map<Long, List<PositionDataDTO>> departmentGroup = CollectionUtil
                .group(positionDTO, PositionDataDTO::getDepartmentId);
        Map<Long, List<PositionDataDTO>> dutyGroup = CollectionUtil.group(positionDTO, PositionDataDTO::getDutyId);

        Collection<Department> departments = getDepartments(departmentGroup.keySet());
        Collection<Duty> duties = getDuties(dutyGroup.keySet());

        for (Department department : departments) {
            List<PositionDataDTO> positionList = departmentGroup.get(department.getDepartmentId());

            if (CollectionUtil.isNotEmpty(positionList)) {
                departmentGroup.get(department.getDepartmentId())
                        .forEach(dto -> dto.setDepartmentName(department.getDepartmentName()));
            }
        }

        for (Duty duty : duties) {
            List<PositionDataDTO> positionList = dutyGroup.get(duty.getDutyId());

            if (CollectionUtil.isNotEmpty(positionList)) {
                positionList.forEach(dto -> dto.setDutyName(duty.getDutyName()));
            }
        }

        return positionDTO;
    }

    private Collection<Department> getDepartments(Collection<Long> departmentIdList) {
        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDepartmentIds(departmentIdList);

        return departmentService.selectAll(searchParams);
    }

    private Collection<Duty> getDuties(Collection<Long> dutyIdList) {
        DutySearchParams searchParams = new DutySearchParams();
        searchParams.setDutyIds(dutyIdList);

        return dutyService.selectAll(searchParams);
    }

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setDutyService(DutyService dutyService) {
        this.dutyService = dutyService;
    }
}
