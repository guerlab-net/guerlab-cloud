package net.guerlab.smart.platform.user.web.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.server.controller.BaseController;
import net.guerlab.smart.platform.user.core.domain.DepartmentTypeDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentTypeHasMappingException;
import net.guerlab.smart.platform.user.core.exception.DepartmentTypeInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentTypeSearchParams;
import net.guerlab.smart.platform.user.service.entity.DepartmentType;
import net.guerlab.smart.platform.user.service.service.DepartmentService;
import net.guerlab.smart.platform.user.service.service.DepartmentTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门类型
 *
 * @author guer
 */
@Api(tags = "部门类型")
@RestController("/user/departmentType")
@RequestMapping("/user/departmentType")
public class DepartmentTypeController
        extends BaseController<DepartmentTypeDTO, DepartmentType, DepartmentTypeService, DepartmentTypeSearchParams, String> {

    private DepartmentService departmentService;

    @Override
    public void copyProperties(DepartmentTypeDTO dto, DepartmentType entity, String id) {
        super.copyProperties(dto, entity, id);
        if (StringUtils.isNotBlank(id)) {
            entity.setDepartmentTypeKey(id);
        }
    }

    @Override
    protected ApplicationException nullPointException() {
        return new DepartmentTypeInvalidException();
    }

    @Override
    public void delete(@ApiParam(value = "id", required = true) @PathVariable String id,
            @ApiParam(value = "强制删除标志") @RequestParam(required = false) Boolean force) {
        findOne0(id);

        DepartmentSearchParams searchParams = new DepartmentSearchParams();
        searchParams.setDepartmentTypeKey(id);

        if (departmentService.selectCount(searchParams) != 0) {
            throw new DepartmentTypeHasMappingException();
        }

        service.deleteById(id, force);
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

}
