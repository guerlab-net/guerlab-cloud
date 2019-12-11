package net.guerlab.smart.platform.user.web.controller.commons;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.commons.util.TreeUtils;
import net.guerlab.smart.platform.user.core.domain.SimpleDepartmentDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.service.service.DepartmentService;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * 部门
 *
 * @author guer
 */
@Api(tags = "部门")
@RestController("/commons/simpleDepartment")
@RequestMapping("/commons/simpleDepartment")
public class SimpleDepartmentController {

    private DepartmentService service;

    @ApiOperation("查询详情")
    @GetMapping("/{id}")
    public SimpleDepartmentDTO findOne(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return BeanConvertUtils.toObject(service.selectByIdOptional(id).orElseThrow(DepartmentInvalidException::new),
                SimpleDepartmentDTO.class);
    }

    @ApiOperation("查询列表")
    @GetMapping
    public ListObject<SimpleDepartmentDTO> findList(UserSearchParams searchParams) {
        return BeanConvertUtils.toListObject(service.selectPage(searchParams), SimpleDepartmentDTO.class);
    }

    @ApiOperation("查询全部")
    @GetMapping("/all")
    public Collection<SimpleDepartmentDTO> findAll(UserSearchParams searchParams) {
        return BeanConvertUtils.toList(service.selectAll(searchParams), SimpleDepartmentDTO.class);
    }

    @ApiOperation("获取树状部门结构")
    @GetMapping("/tree")
    public Collection<SimpleDepartmentDTO> tree() {
        return TreeUtils.tree(findAll(null));
    }

    @Autowired
    public void setService(DepartmentService service) {
        this.service = service;
    }

}
