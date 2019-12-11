package net.guerlab.smart.platform.user.web.controller.inside;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.core.domain.PositionDTO;
import net.guerlab.smart.platform.user.core.exception.PositionInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.smart.platform.user.service.service.PositionService;
import net.guerlab.web.result.ListObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职位
 *
 * @author guer
 */
@Api(tags = "职位")
@RestController("/inside/position")
@RequestMapping("/inside/position")
public class PositionController {

    private PositionService service;

    @PostMapping("/{id}")
    public PositionDTO findOne(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return service.selectByIdOptional(id).orElseThrow(PositionInvalidException::new).toDTO();
    }

    @PostMapping
    public ListObject<PositionDTO> findList(@RequestBody PositionSearchParams searchParams) {
        return BeanConvertUtils.toListObject(service.selectPage(searchParams));
    }

    @PostMapping("/all")
    public List<PositionDTO> findAll(@RequestBody PositionSearchParams searchParams) {
        return BeanConvertUtils.toList(service.selectAll(searchParams));
    }

    @Autowired
    public void setService(PositionService service) {
        this.service = service;
    }
}
