package net.guerlab.smart.platform.user.server.service.impl;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.commons.util.OrderEntityUtils;
import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.user.core.UserAuthConstants;
import net.guerlab.smart.platform.user.core.exception.PositionIdInvalidException;
import net.guerlab.smart.platform.user.core.exception.PositionInvalidException;
import net.guerlab.smart.platform.user.core.exception.PositionNameInvalidException;
import net.guerlab.smart.platform.user.core.exception.SystemPositionCannotOperationException;
import net.guerlab.smart.platform.user.core.searchparams.TakeOfficeSearchParams;
import net.guerlab.smart.platform.user.server.entity.Position;
import net.guerlab.smart.platform.user.server.mapper.PositionMapper;
import net.guerlab.smart.platform.user.server.service.DepartmentPositionDistributionService;
import net.guerlab.smart.platform.user.server.service.PositionService;
import net.guerlab.smart.platform.user.server.service.TakeOfficeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 职位服务实现
 *
 * @author guer
 */
@Service
public class PositionServiceImpl extends BaseServiceImpl<Position, Long, PositionMapper> implements PositionService {

    private TakeOfficeService takeOfficeService;

    private DepartmentPositionDistributionService distributionService;

    @Override
    protected void insertBefore(Position entity) {
        String positionName = StringUtils.trimToNull(entity.getPositionName());
        if (positionName == null) {
            throw new PositionNameInvalidException();
        }

        entity.setPositionId(sequence.nextId());
        entity.setPositionName(positionName);
        entity.setUpdateTime(LocalDateTime.now());
        OrderEntityUtils.propertiesCheck(entity);
    }

    @Override
    protected void updateBefore(Position entity) {
        systemPositionCheck(entity);
        entity.setUpdateTime(LocalDateTime.now());
    }

    @Override
    protected void deleteBefore(Position entity, Boolean force) {
        systemPositionCheck(entity);
    }

    @Override
    protected void deleteByIdBefore(Long positionId, Boolean force) {
        idCheck(positionId);
    }

    @Override
    protected void deleteAfter(Position entity, Boolean force) {
        deleteByIdAfter(entity.getPositionId(), force);
    }

    @Override
    protected void deleteByIdAfter(Long positionId, Boolean force) {
        if (!NumberHelper.greaterZero(positionId)) {
            throw new PositionIdInvalidException();
        }

        TakeOfficeSearchParams searchParams = new TakeOfficeSearchParams();
        searchParams.setPositionId(positionId);

        takeOfficeService.delete(searchParams);

        distributionService.deleteByPositionId(positionId);
    }

    private void systemPositionCheck(Position entity) {
        if (entity == null) {
            throw new PositionInvalidException();
        }

        idCheck(entity.getPositionId());
    }

    private void idCheck(Long positionId) {
        if (!NumberHelper.greaterZero(positionId)) {
            throw new PositionIdInvalidException();
        }

        if (positionId < UserAuthConstants.SYSTEM_POSITION_ID_RANGE) {
            throw new SystemPositionCannotOperationException();
        }
    }

    @Autowired
    public void setTakeOfficeService(TakeOfficeService takeOfficeService) {
        this.takeOfficeService = takeOfficeService;
    }

    @Autowired
    public void setDistributionService(DepartmentPositionDistributionService distributionService) {
        this.distributionService = distributionService;
    }

}
