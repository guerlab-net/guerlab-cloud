package net.guerlab.smart.platform.user.api.feign;

import net.guerlab.smart.platform.user.api.feign.factory.FeignDepartmentApiFallbackFactory;
import net.guerlab.smart.platform.user.core.domain.DepartmentDTO;
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
 * 部门服务接口
 *
 * @author guer
 */
@FeignClient(name = "user-api/inside/department", fallbackFactory = FeignDepartmentApiFallbackFactory.class)
public interface FeignDepartmentApi {

    /**
     * 根据部门id查询部门
     *
     * @param departmentId
     *         部门id
     * @return 部门
     */
    @GetMapping("/{departmentId}")
    Result<DepartmentDTO> findOne(@PathVariable("departmentId") Long departmentId);

    /**
     * 根据搜索参数查询部门列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门列表
     */
    @PostMapping
    Result<ListObject<DepartmentDTO>> findList(@RequestBody Map<String, Object> searchParams);

    /**
     * 根据搜索参数查询部门列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门列表
     */
    @PostMapping("/all")
    Result<List<DepartmentDTO>> findAll(@RequestBody Map<String, Object> searchParams);
}
