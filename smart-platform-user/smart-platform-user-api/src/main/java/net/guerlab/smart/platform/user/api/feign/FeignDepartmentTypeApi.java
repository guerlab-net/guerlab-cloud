package net.guerlab.smart.platform.user.api.feign;

import net.guerlab.smart.platform.user.api.feign.factory.FeignDepartmentTypeApiFallbackFactory;
import net.guerlab.smart.platform.user.core.domain.DepartmentTypeDTO;
import net.guerlab.web.result.ListObject;
import net.guerlab.web.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 部门类型服务接口
 *
 * @author guer
 */
@FeignClient(name = "user-api/inside/departmentType", fallbackFactory = FeignDepartmentTypeApiFallbackFactory.class)
public interface FeignDepartmentTypeApi {

    /**
     * 根据部门类型关键字查询部门类型
     *
     * @param departmentTypeKey
     *         部门类型关键字
     * @return 部门类型
     */
    @PostMapping("/{departmentTypeKey}")
    Result<DepartmentTypeDTO> findOne(@PathVariable("departmentTypeKey") String departmentTypeKey);

    /**
     * 根据搜索参数查询部门类型列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门类型列表
     */
    @PostMapping
    Result<ListObject<DepartmentTypeDTO>> findList(@RequestBody Map<String, Object> searchParams);

    /**
     * 根据搜索参数查询部门类型列表
     *
     * @param searchParams
     *         搜索参数
     * @return 部门类型列表
     */
    @PostMapping("/all")
    Result<List<DepartmentTypeDTO>> findAll(@RequestBody Map<String, Object> searchParams);
}
