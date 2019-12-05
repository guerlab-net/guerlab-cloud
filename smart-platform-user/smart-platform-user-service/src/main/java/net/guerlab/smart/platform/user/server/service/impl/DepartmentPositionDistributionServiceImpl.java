package net.guerlab.smart.platform.user.server.service.impl;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.server.service.BaseBatchServiceImpl;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentPositionDistributionSearchParams;
import net.guerlab.smart.platform.user.server.entity.DepartmentPositionDistribution;
import net.guerlab.smart.platform.user.server.mapper.DepartmentPositionDistributionMapper;
import net.guerlab.smart.platform.user.server.service.DepartmentPositionDistributionService;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 部门职位分配服务
 *
 * @author guer
 */
@Service
public class DepartmentPositionDistributionServiceImpl
        extends BaseBatchServiceImpl<DepartmentPositionDistribution, Long, DepartmentPositionDistributionMapper>
        implements DepartmentPositionDistributionService {

    @Override
    protected DepartmentPositionDistribution batchSaveBefore(DepartmentPositionDistribution entity) {
        return entity != null && NumberHelper.allGreaterZero(entity.getDepartmentId(), entity.getPositionId()) ?
                entity :
                null;
    }

    @Override
    public Class<DepartmentPositionDistribution> getEntityClass() {
        return DepartmentPositionDistribution.class;
    }

    @Override
    public Collection<DepartmentPositionDistribution> find(DepartmentPositionDistributionSearchParams searchParams) {
        return mapper.selectByExample(getExample(searchParams));
    }

    @Override
    public void save(Collection<DepartmentPositionDistribution> list) {
        replaceBatchInsert(list);
    }

    @Override
    public void delete(DepartmentPositionDistributionSearchParams searchParams) {
        mapper.deleteByExample(getExample(searchParams));
    }
}
