package net.guerlab.smart.platform.user.service.service;

import net.guerlab.commons.collection.CollectionUtil;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.TakeOfficeDataDTO;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.service.entity.Department;
import net.guerlab.smart.platform.user.service.entity.Position;
import net.guerlab.smart.platform.user.service.entity.TakeOffice;
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
public class TakeOfficeGetHandler {

    private TakeOfficeService takeOfficeService;

    private DepartmentService departmentService;

    private PositionService positionService;

    /**
     * 获取职位信息列表
     *
     * @param userId
     *         用户ID
     * @return 职位信息列表
     */
    public List<TakeOfficeDataDTO> getTakeOffice(Long userId) {
        Collection<TakeOffice> takeOffices = takeOfficeService.findByUserId(userId);

        if (CollectionUtil.isEmpty(takeOffices)) {
            return Collections.emptyList();
        }

        List<TakeOfficeDataDTO> takeOfficeDTO = BeanConvertUtils.toList(takeOffices, TakeOfficeDataDTO.class);

        Map<Long, List<TakeOfficeDataDTO>> departmentGroup = CollectionUtil
                .group(takeOfficeDTO, TakeOfficeDataDTO::getDepartmentId);
        Map<Long, List<TakeOfficeDataDTO>> positionGroup = CollectionUtil
                .group(takeOfficeDTO, TakeOfficeDataDTO::getPositionId);

        Collection<Department> departments = getDepartments(departmentGroup.keySet());
        Collection<Position> positions = getPositions(positionGroup.keySet());

        for (Department department : departments) {
            List<TakeOfficeDataDTO> takeOfficeList = departmentGroup.get(department.getDepartmentId());

            if (CollectionUtil.isNotEmpty(takeOfficeList)) {
                departmentGroup.get(department.getDepartmentId())
                        .forEach(dto -> dto.setDepartmentName(department.getDepartmentName()));
            }
        }

        for (Position position : positions) {
            List<TakeOfficeDataDTO> takeOfficeList = positionGroup.get(position.getPositionId());

            if (CollectionUtil.isNotEmpty(takeOfficeList)) {
                takeOfficeList.forEach(dto -> dto.setPositionName(position.getPositionName()));
            }
        }

        return takeOfficeDTO;
    }

    private Collection<Department> getDepartments(Collection<Long> departmentIdList) {
        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDepartmentIds(departmentIdList);

        return departmentService.selectAll(searchParams);
    }

    private Collection<Position> getPositions(Collection<Long> positionIdList) {
        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setPositionIds(positionIdList);

        return positionService.selectAll(searchParams);
    }

    @Autowired
    public void setTakeOfficeService(TakeOfficeService takeOfficeService) {
        this.takeOfficeService = takeOfficeService;
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }
}
