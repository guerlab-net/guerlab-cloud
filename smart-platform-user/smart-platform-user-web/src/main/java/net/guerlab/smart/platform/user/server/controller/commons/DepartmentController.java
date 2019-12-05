package net.guerlab.smart.platform.user.server.controller.commons;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.commons.util.TreeUtils;
import net.guerlab.smart.platform.server.controller.BaseFindController;
import net.guerlab.smart.platform.user.core.domain.DepartmentDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.server.entity.Department;
import net.guerlab.smart.platform.user.server.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * 部门
 *
 * @author guer
 */
@Api(tags = "部门")
@RestController("/commons/department")
@RequestMapping("/commons/department")
public class DepartmentController
        extends BaseFindController<DepartmentDTO, Department, DepartmentService, DepartmentSearchParams, Long> {

    @Override
    protected ApplicationException nullPointException() {
        return new DepartmentInvalidException();
    }

    @ApiOperation("获取树状部门结构")
    @GetMapping("/tree")
    public Collection<DepartmentDTO> tree() {
        return TreeUtils.tree(findAll(null));
    }

}
