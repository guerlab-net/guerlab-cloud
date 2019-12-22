package net.guerlab.smart.platform.user.web.controller.inside;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.DutyDTO;
import net.guerlab.smart.platform.user.core.exception.DutyInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DutySearchParams;
import net.guerlab.smart.platform.user.service.service.DutyService;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职务
 *
 * @author guer
 */
@Api(tags = "职务")
@RestController("/inside/duty")
@RequestMapping("/inside/duty")
public class DutyController {

    private DutyService service;

    @GetMapping("/{id}")
    public DutyDTO findOne(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return service.selectByIdOptional(id).orElseThrow(DutyInvalidException::new).toDTO();
    }

    @PostMapping
    public ListObject<DutyDTO> findList(@RequestBody DutySearchParams searchParams) {
        return BeanConvertUtils.toListObject(service.selectPage(searchParams));
    }

    @PostMapping("/all")
    public List<DutyDTO> findAll(@RequestBody DutySearchParams searchParams) {
        return BeanConvertUtils.toList(service.selectAll(searchParams));
    }

    @Autowired
    public void setService(DutyService service) {
        this.service = service;
    }
}
