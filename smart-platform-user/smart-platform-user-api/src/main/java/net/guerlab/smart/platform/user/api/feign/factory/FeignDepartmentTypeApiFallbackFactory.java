package net.guerlab.smart.platform.user.api.feign.factory;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.smart.platform.user.api.feign.FeignDepartmentTypeApi;
import net.guerlab.smart.platform.user.core.domain.DepartmentTypeDTO;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.ListObject;
import net.guerlab.web.result.Result;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 部门类型服务快速失败类构建工厂
 *
 * @author guer
 */
public class FeignDepartmentTypeApiFallbackFactory implements FallbackFactory<FeignDepartmentTypeApi> {

    @Override
    public FeignDepartmentTypeApi create(Throwable cause) {
        return new FeignDepartmentTypeApiFallback(cause);
    }

    /**
     * 部门类型服务快速失败实现
     */
    @Slf4j
    @AllArgsConstructor
    static class FeignDepartmentTypeApiFallback implements FeignDepartmentTypeApi {

        private final Throwable cause;

        @Override
        public Result<DepartmentTypeDTO> findOne(String departmentTypeKey) {
            log.error("findOne fallback", cause);
            return new Fail<>("fallback");
        }

        @Override
        public Result<ListObject<DepartmentTypeDTO>> findList(Map<String, Object> searchParams) {
            log.error("findList fallback", cause);
            return new Fail<>("fallback", ListObject.empty());
        }

        @Override
        public Result<List<DepartmentTypeDTO>> findAll(Map<String, Object> searchParams) {
            log.error("findAll fallback", cause);
            return new Fail<>("fallback", Collections.emptyList());
        }
    }
}
