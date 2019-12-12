package net.guerlab.smart.platform.user.web.controller.inside;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.DepartmentTypeDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentTypeInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentTypeSearchParams;
import net.guerlab.smart.platform.user.service.service.DepartmentTypeService;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门类型
 *
 * @author guer
 */
@Api(tags = "部门类型")
@RestController("/inside/departmentType")
@RequestMapping("/inside/departmentType")
public class DepartmentTypeController {

    private DepartmentTypeService service;

    @GetMapping("/{id}")
    public DepartmentTypeDTO findOne(@ApiParam(value = "id", required = true) @PathVariable String id) {
        return service.selectByIdOptional(id).orElseThrow(DepartmentTypeInvalidException::new).toDTO();
    }

    @PostMapping
    public ListObject<DepartmentTypeDTO> findList(@RequestBody DepartmentTypeSearchParams searchParams) {
        return BeanConvertUtils.toListObject(service.selectPage(searchParams));
    }

    @PostMapping("/all")
    public List<DepartmentTypeDTO> findAll(@RequestBody DepartmentTypeSearchParams searchParams) {
        return BeanConvertUtils.toList(service.selectAll(searchParams));
    }

    @Autowired
    public void setService(DepartmentTypeService service) {
        this.service = service;
    }

}
