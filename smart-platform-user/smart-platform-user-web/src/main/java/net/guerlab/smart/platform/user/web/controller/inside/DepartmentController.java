package net.guerlab.smart.platform.user.web.controller.inside;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.DepartmentDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentTypeSearchParams;
import net.guerlab.smart.platform.user.service.service.DepartmentService;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门
 *
 * @author guer
 */
@Api(tags = "部门")
@RestController("/inside/department")
@RequestMapping("/inside/department")
public class DepartmentController {

    private DepartmentService service;

    @PostMapping("/{id}")
    public DepartmentDTO findOne(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return service.selectByIdOptional(id).orElseThrow(DepartmentInvalidException::new).toDTO();
    }

    @PostMapping
    public ListObject<DepartmentDTO> findList(@RequestBody DepartmentTypeSearchParams searchParams) {
        return BeanConvertUtils.toListObject(service.selectPage(searchParams));
    }

    @PostMapping("/all")
    public List<DepartmentDTO> findAll(@RequestBody DepartmentTypeSearchParams searchParams) {
        return BeanConvertUtils.toList(service.selectAll(searchParams));
    }

    @Autowired
    public void setService(DepartmentService service) {
        this.service = service;
    }
}
