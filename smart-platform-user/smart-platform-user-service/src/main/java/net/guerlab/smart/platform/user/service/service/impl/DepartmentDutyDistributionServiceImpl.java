package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.server.service.BaseBatchServiceImpl;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentDutyDistributionSearchParams;
import net.guerlab.smart.platform.user.service.entity.DepartmentDutyDistribution;
import net.guerlab.smart.platform.user.service.mapper.DepartmentDutyDistributionMapper;
import net.guerlab.smart.platform.user.service.service.DepartmentDutyDistributionService;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 部门职务分配服务
 *
 * @author guer
 */
@Service
public class DepartmentDutyDistributionServiceImpl
        extends BaseBatchServiceImpl<DepartmentDutyDistribution, Long, DepartmentDutyDistributionMapper>
        implements DepartmentDutyDistributionService {

    @Override
    protected DepartmentDutyDistribution batchSaveBefore(DepartmentDutyDistribution entity) {
        return entity != null && NumberHelper.allGreaterZero(entity.getDepartmentId(), entity.getDutyId()) ?
                entity :
                null;
    }

    @Override
    public Class<DepartmentDutyDistribution> getEntityClass() {
        return DepartmentDutyDistribution.class;
    }

    @Override
    public Collection<DepartmentDutyDistribution> find(DepartmentDutyDistributionSearchParams searchParams) {
        return mapper.selectByExample(getExample(searchParams));
    }

    @Override
    public void save(Collection<DepartmentDutyDistribution> list) {
        replaceBatchInsert(list);
    }

    @Override
    public void delete(DepartmentDutyDistributionSearchParams searchParams) {
        mapper.deleteByExample(getExample(searchParams));
    }
}
