package net.guerlab.smart.platform.user.server.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.SimpleUserDTO;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.server.service.UserService;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * 简单用户
 *
 * @author guer
 */
@Api(tags = "简单用户")
@RestController("/user/simpleUser")
@RequestMapping("/user/simpleUser")
public class SimpleUserController {

    private UserService service;

    @ApiOperation("查询详情")
    @GetMapping("/{id}")
    public SimpleUserDTO findOne(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return BeanConvertUtils
                .toObject(service.selectByIdOptional(id).orElseThrow(UserInvalidException::new), SimpleUserDTO.class);
    }

    @ApiOperation("查询列表")
    @GetMapping
    public ListObject<SimpleUserDTO> findList(UserSearchParams searchParams) {
        return BeanConvertUtils.toListObject(service.queryPage(searchParams), SimpleUserDTO.class);
    }

    @ApiOperation("查询全部")
    @GetMapping("/all")
    public Collection<SimpleUserDTO> findAll(UserSearchParams searchParams) {
        return BeanConvertUtils.toList(service.queryAll(searchParams), SimpleUserDTO.class);
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }
}
