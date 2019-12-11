package net.guerlab.smart.platform.user.web.controller.inside;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.SimpleDepartmentDTO;
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
@RestController("/inside/simpleDepartment")
@RequestMapping("/inside/simpleDepartment")
public class SimpleDepartmentController {

    private DepartmentService service;

    @PostMapping("/{id}")
    public SimpleDepartmentDTO findOne(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return BeanConvertUtils.toObject(service.selectByIdOptional(id).orElseThrow(DepartmentInvalidException::new),
                SimpleDepartmentDTO.class);
    }

    @PostMapping
    public ListObject<SimpleDepartmentDTO> findList(@RequestBody DepartmentTypeSearchParams searchParams) {
        return BeanConvertUtils.toListObject(service.selectPage(searchParams), SimpleDepartmentDTO.class);
    }

    @PostMapping("/all")
    public List<SimpleDepartmentDTO> findAll(@RequestBody DepartmentTypeSearchParams searchParams) {
        return BeanConvertUtils.toList(service.selectAll(searchParams), SimpleDepartmentDTO.class);
    }

    @Autowired
    public void setService(DepartmentService service) {
        this.service = service;
    }
}
