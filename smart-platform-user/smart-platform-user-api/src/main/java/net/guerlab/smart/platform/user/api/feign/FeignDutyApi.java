package net.guerlab.smart.platform.user.api.feign;

import net.guerlab.smart.platform.user.api.feign.factory.FeignDutyApiFallbackFactory;
import net.guerlab.smart.platform.user.core.domain.DutyDTO;
import net.guerlab.web.result.ListObject;
import net.guerlab.web.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 职务服务接口
 *
 * @author guer
 */
@FeignClient(name = "user-api/inside/duty", fallbackFactory = FeignDutyApiFallbackFactory.class)
public interface FeignDutyApi {

    /**
     * 根据职务id查询职务
     *
     * @param dutyId
     *         职务id
     * @return 职务
     */
    @GetMapping("/{dutyId}")
    Result<DutyDTO> findOne(@PathVariable("dutyId") Long dutyId);

    /**
     * 根据搜索参数查询职务列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职务列表
     */
    @PostMapping
    Result<ListObject<DutyDTO>> findList(@RequestBody Map<String, Object> searchParams);

    /**
     * 根据搜索参数查询职务列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职务列表
     */
    @PostMapping("/all")
    Result<List<DutyDTO>> findAll(@RequestBody Map<String, Object> searchParams);
}
