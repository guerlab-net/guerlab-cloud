package net.guerlab.smart.platform.user.service.service.impl;

import net.guerlab.smart.platform.commons.util.OrderEntityUtils;
import net.guerlab.smart.platform.server.service.BaseServiceImpl;
import net.guerlab.smart.platform.user.core.exception.DepartmentTypeKeyInvalidException;
import net.guerlab.smart.platform.user.core.exception.DepartmentTypeKeyRepeatException;
import net.guerlab.smart.platform.user.core.exception.DepartmentTypeNameInvalidException;
import net.guerlab.smart.platform.user.core.exception.DepartmentTypeNameRepeatException;
import net.guerlab.smart.platform.user.service.entity.DepartmentType;
import net.guerlab.smart.platform.user.service.mapper.DepartmentTypeMapper;
import net.guerlab.smart.platform.user.service.service.DepartmentService;
import net.guerlab.smart.platform.user.service.service.DepartmentTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 部门类型服务实现
 *
 * @author guer
 */
@Service
public class DepartmentTypeServiceImpl extends BaseServiceImpl<DepartmentType, String, DepartmentTypeMapper>
        implements DepartmentTypeService {

    private DepartmentService departmentService;

    @Override
    protected void insertBefore(DepartmentType entity) {
        String departmentTypeKey = StringUtils.trimToNull(entity.getDepartmentTypeKey());
        String departmentTypeName = StringUtils.trimToNull(entity.getDepartmentTypeName());

        if (departmentTypeKey == null) {
            throw new DepartmentTypeKeyInvalidException();
        }
        if (departmentTypeName == null) {
            throw new DepartmentTypeNameInvalidException();
        }

        if (selectById(departmentTypeKey) != null) {
            throw new DepartmentTypeKeyRepeatException();
        }

        if (selectByDepartmentTypeName(departmentTypeName) != null) {
            throw new DepartmentTypeNameRepeatException();
        }

        OrderEntityUtils.propertiesCheck(entity);
    }

    @Override
    protected void updateAfter(DepartmentType entity) {
        departmentService.updateByDepartmentType(entity);
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}
