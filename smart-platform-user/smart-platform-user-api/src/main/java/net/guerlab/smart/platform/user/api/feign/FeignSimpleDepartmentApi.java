package net.guerlab.smart.platform.user.api.feign;

import net.guerlab.smart.platform.user.api.feign.factory.FeignSimpleDepartmentApiFallbackFactory;
import net.guerlab.smart.platform.user.core.domain.SimpleDepartmentDTO;
import net.guerlab.web.result.ListObject;
import net.guerlab.web.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 简单部门服务接口
 *
 * @author guer
 */
@FeignClient(name = "user-api/inside/simpleDepartment", fallbackFactory = FeignSimpleDepartmentApiFallbackFactory.class)
public interface FeignSimpleDepartmentApi {

    /**
     * 根据部门id查询部门
     *
     * @param departmentId
     *         部门id
     * @return 部门
     */
    @PostMapping("/{departmentId}")
    Result<SimpleDepartmentDTO> findOne(@PathVariable("departmentId") Long departmentId);

    /**
     * 根据搜索参数查询部门列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门列表
     */
    @PostMapping
    Result<ListObject<SimpleDepartmentDTO>> findList(@RequestBody Map<String, Object> searchParams);

    /**
     * 根据搜索参数查询部门列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门列表
     */
    @PostMapping("/all")
    Result<List<SimpleDepartmentDTO>> findAll(@RequestBody Map<String, Object> searchParams);
}
