package net.guerlab.smart.platform.user.api.feign;

import net.guerlab.smart.platform.user.api.feign.factory.FeignSimpleUserApiFallbackFactory;
import net.guerlab.smart.platform.user.core.domain.SimpleUserDTO;
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
 * 简单用户服务接口
 *
 * @author guer
 */
@FeignClient(name = "user-api/inside/simpleUser", fallbackFactory = FeignSimpleUserApiFallbackFactory.class)
public interface FeignSimpleUserApi {

    /**
     * 根据用户id查询用户
     *
     * @param userId
     *         用户id
     * @return 用户
     */
    @GetMapping("/{userId}")
    Result<SimpleUserDTO> findOne(@PathVariable("userId") Long userId);

    /**
     * 根据搜索参数查询用户列表
     *
     * @param searchParams
     *         搜索参数
     * @return 用户列表
     */
    @PostMapping
    Result<ListObject<SimpleUserDTO>> findList(@RequestBody Map<String, Object> searchParams);

    /**
     * 根据搜索参数查询用户列表
     *
     * @param searchParams
     *         搜索参数
     * @return 用户列表
     */
    @PostMapping("/all")
    Result<List<SimpleUserDTO>> findAll(@RequestBody Map<String, Object> searchParams);
}
