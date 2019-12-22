package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.server.service.BaseBatchServiceImpl;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.service.entity.Position;
import net.guerlab.smart.platform.user.service.mapper.PositionMapper;
import net.guerlab.smart.platform.user.service.service.PositionService;
import net.guerlab.web.result.ListObject;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 职位服务实现
 *
 * @author guer
 */
@Service
public class PositionServiceImpl extends BaseBatchServiceImpl<Position, Long, PositionMapper>
        implements PositionService {

    @Override
    protected Position batchSaveBefore(Position entity) {
        return entity != null && NumberHelper
                .allGreaterZero(entity.getDepartmentId(), entity.getDutyId(), entity.getUserId()) ? entity : null;
    }

    @Override
    public Class<Position> getEntityClass() {
        return Position.class;
    }

    @Override
    public Position findOne(PositionSearchParams searchParams) {
        return mapper.selectOneByExample(getExample(searchParams));
    }

    @Override
    public Collection<Position> findList(PositionSearchParams searchParams) {
        return mapper.selectByExample(getExample(searchParams));
    }

    @Override
    public ListObject<Position> findPage(PositionSearchParams searchParams) {
        return super.selectPage(searchParams);
    }

    @Override
    public void save(Collection<Position> list) {
        replaceBatchInsert(list);
    }

    @Override
    public void delete(PositionSearchParams searchParams) {
        mapper.deleteByExample(getExample(searchParams));
    }

    @Override
    public void delete(Position entity) {
        mapper.delete(entity);
    }
}
