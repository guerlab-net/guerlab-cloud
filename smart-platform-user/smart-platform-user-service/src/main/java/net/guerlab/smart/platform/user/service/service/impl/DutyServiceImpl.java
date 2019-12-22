package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.util.OrderEntityUtils;
import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.user.core.UserAuthConstants;
import net.guerlab.smart.platform.user.core.exception.DutyIdInvalidException;
import net.guerlab.smart.platform.user.core.exception.DutyInvalidException;
import net.guerlab.smart.platform.user.core.exception.DutyNameInvalidException;
import net.guerlab.smart.platform.user.core.exception.SystemDutyCannotOperationException;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.service.entity.Duty;
import net.guerlab.smart.platform.user.service.mapper.DutyMapper;
import net.guerlab.smart.platform.user.service.service.DepartmentDutyDistributionService;
import net.guerlab.smart.platform.user.service.service.DutyService;
import net.guerlab.smart.platform.user.service.service.PositionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 职务服务实现
 *
 * @author guer
 */
@Service
public class DutyServiceImpl extends BaseServiceImpl<Duty, Long, DutyMapper> implements DutyService {

    private PositionService positionService;

    private DepartmentDutyDistributionService distributionService;

    @Override
    protected void insertBefore(Duty entity) {
        String dutyName = StringUtils.trimToNull(entity.getDutyName());
        if (dutyName == null) {
            throw new DutyNameInvalidException();
        }

        entity.setDutyId(sequence.nextId());
        entity.setDutyName(dutyName);
        entity.setUpdateTime(LocalDateTime.now());
        OrderEntityUtils.propertiesCheck(entity);
    }

    @Override
    protected void updateBefore(Duty entity) {
        systemDutyCheck(entity);
        entity.setUpdateTime(LocalDateTime.now());
    }

    @Override
    protected void deleteBefore(Duty entity, Boolean force) {
        systemDutyCheck(entity);
    }

    @Override
    protected void deleteByIdBefore(Long dutyId, Boolean force) {
        idCheck(dutyId);
    }

    @Override
    protected void deleteAfter(Duty entity, Boolean force) {
        deleteByIdAfter(entity.getDutyId(), force);
    }

    @Override
    protected void deleteByIdAfter(Long dutyId, Boolean force) {
        if (!NumberHelper.greaterZero(dutyId)) {
            throw new DutyIdInvalidException();
        }

        PositionSearchParams searchParams = new PositionSearchParams();
        searchParams.setDutyId(dutyId);

        positionService.delete(searchParams);

        distributionService.deleteByDutyId(dutyId);
    }

    private void systemDutyCheck(Duty entity) {
        if (entity == null) {
            throw new DutyInvalidException();
        }

        idCheck(entity.getDutyId());
    }

    private void idCheck(Long dutyId) {
        if (!NumberHelper.greaterZero(dutyId)) {
            throw new DutyIdInvalidException();
        }

        if (dutyId < UserAuthConstants.SYSTEM_POSITION_ID_RANGE) {
            throw new SystemDutyCannotOperationException();
        }
    }

    @Autowired
    public void setPositionService(PositionService positionService) {
        this.positionService = positionService;
    }

    @Autowired
    public void setDistributionService(DepartmentDutyDistributionService distributionService) {
        this.distributionService = distributionService;
    }

}
