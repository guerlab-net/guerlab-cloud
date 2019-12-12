package net.guerlab.smart.platform.user.web.controller.inside;

import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.SimpleUserDTO;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.service.service.UserService;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 用户
 *
 * @author guer
 */
@RestController("/inside/simpleUser")
@RequestMapping("/inside/simpleUser")
public class SimpleUserController {

    private UserService service;

    @GetMapping("/{id}")
    public SimpleUserDTO findOne(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return BeanConvertUtils
                .toObject(service.selectByIdOptional(id).orElseThrow(UserInvalidException::new), SimpleUserDTO.class);
    }

    @PostMapping
    public ListObject<SimpleUserDTO> findList(@RequestBody UserSearchParams searchParams) {
        return BeanConvertUtils.toListObject(service.queryPage(searchParams), SimpleUserDTO.class);
    }

    @PostMapping("/all")
    public Collection<SimpleUserDTO> findAll(@RequestBody UserSearchParams searchParams) {
        return BeanConvertUtils.toList(service.queryAll(searchParams), SimpleUserDTO.class);
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

}
