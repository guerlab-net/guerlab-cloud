package net.guerlab.smart.platform.user.api.feign;

import net.guerlab.smart.platform.user.api.feign.factory.FeignPositionApiFallbackFactory;
import net.guerlab.smart.platform.user.core.domain.PositionDTO;
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
 * 职位服务接口
 *
 * @author guer
 */
@FeignClient(name = "user-api/inside/position", fallbackFactory = FeignPositionApiFallbackFactory.class)
public interface FeignPositionApi {

    /**
     * 根据职位id查询职位
     *
     * @param positionId
     *         职位id
     * @return 职位
     */
    @GetMapping("/{positionId}")
    Result<PositionDTO> findOne(@PathVariable("positionId") Long positionId);

    /**
     * 根据搜索参数查询职位列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职位列表
     */
    @PostMapping
    Result<ListObject<PositionDTO>> findList(@RequestBody Map<String, Object> searchParams);

    /**
     * 根据搜索参数查询职位列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职位列表
     */
    @PostMapping("/all")
    Result<List<PositionDTO>> findAll(@RequestBody Map<String, Object> searchParams);
}
