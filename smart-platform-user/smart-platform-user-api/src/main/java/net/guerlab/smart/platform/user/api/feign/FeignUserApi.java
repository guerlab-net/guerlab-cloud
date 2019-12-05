package net.guerlab.smart.platform.user.api.feign;

import net.guerlab.smart.platform.user.api.feign.factory.FeignUserApiFallbackFactory;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.web.result.ListObject;
import net.guerlab.web.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 用户服务接口
 *
 * @author guer
 */
@FeignClient(name = "user-api/inside/user", fallbackFactory = FeignUserApiFallbackFactory.class)
public interface FeignUserApi {

    /**
     * 根据用户id查询用户
     *
     * @param userId
     *         用户id
     * @return 用户
     */
    @PostMapping("/{userId}")
    Result<UserDTO> findOne(@PathVariable("userId") Long userId);

    /**
     * 根据搜索参数查询用户列表
     *
     * @param searchParams
     *         搜索参数
     * @return 用户列表
     */
    @PostMapping
    Result<ListObject<UserDTO>> findList(@RequestBody Map<String, Object> searchParams);

    /**
     * 根据搜索参数查询用户列表
     *
     * @param searchParams
     *         搜索参数
     * @return 用户列表
     */
    @PostMapping("/all")
    Result<List<UserDTO>> findAll(@RequestBody Map<String, Object> searchParams);
}
