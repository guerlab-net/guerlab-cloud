package net.guerlab.smart.platform.user.server.service.impl;

import net.guerlab.commons.number.NumberHelper;
import net.guerlab.smart.platform.server.service.BaseBatchServiceImpl;
import net.guerlab.smart.platform.user.server.entity.DepartmentParents;
import net.guerlab.smart.platform.user.server.mapper.DepartmentParentsMapper;
import net.guerlab.smart.platform.user.server.searchparams.DepartmentParentsSearchParams;
import net.guerlab.smart.platform.user.server.service.DepartmentParentsService;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 部门上下级关系服务实现
 *
 * @author guer
 */
@Service
public class DepartmentParentsServiceImpl extends BaseBatchServiceImpl<DepartmentParents, Long, DepartmentParentsMapper>
        implements DepartmentParentsService {

    @Override
    protected DepartmentParents batchSaveBefore(DepartmentParents entity) {
        return entity != null && NumberHelper.allGreaterOrEqualZero(entity.getDepartmentId(), entity.getParentId()) ?
                entity :
                null;
    }

    @Override
    public Class<DepartmentParents> getEntityClass() {
        return DepartmentParents.class;
    }

    @Override
    public Collection<DepartmentParents> findList(DepartmentParentsSearchParams searchParams) {
        return mapper.selectByExample(getExample(searchParams));
    }

    @Override
    public void save(Collection<DepartmentParents> list) {
        replaceBatchInsert(list);
    }

    @Override
    public void delete(DepartmentParentsSearchParams searchParams) {
        mapper.deleteByExample(getExample(searchParams));
    }
}
