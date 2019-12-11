package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.server.service.BaseBatchServiceImpl;
import net.guerlab.smart.platform.user.core.searchparams.TakeOfficeSearchParams;
import net.guerlab.smart.platform.user.service.entity.TakeOffice;
import net.guerlab.smart.platform.user.service.mapper.TakeOfficeMapper;
import net.guerlab.smart.platform.user.service.service.TakeOfficeService;
import net.guerlab.web.result.ListObject;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 任职服务实现
 *
 * @author guer
 */
@Service
public class TakeOfficeServiceImpl extends BaseBatchServiceImpl<TakeOffice, Long, TakeOfficeMapper>
        implements TakeOfficeService {

    @Override
    protected TakeOffice batchSaveBefore(TakeOffice entity) {
        return entity != null && NumberHelper
                .allGreaterZero(entity.getDepartmentId(), entity.getPositionId(), entity.getUserId()) ? entity : null;
    }

    @Override
    public Class<TakeOffice> getEntityClass() {
        return TakeOffice.class;
    }

    @Override
    public TakeOffice findOne(TakeOfficeSearchParams searchParams) {
        return mapper.selectOneByExample(getExample(searchParams));
    }

    @Override
    public Collection<TakeOffice> findList(TakeOfficeSearchParams searchParams) {
        return mapper.selectByExample(getExample(searchParams));
    }

    @Override
    public ListObject<TakeOffice> findPage(TakeOfficeSearchParams searchParams) {
        return super.selectPage(searchParams);
    }

    @Override
    public void save(Collection<TakeOffice> list) {
        replaceBatchInsert(list);
    }

    @Override
    public void delete(TakeOfficeSearchParams searchParams) {
        mapper.deleteByExample(getExample(searchParams));
    }

    @Override
    public void delete(TakeOffice entity) {
        mapper.delete(entity);
    }
}
