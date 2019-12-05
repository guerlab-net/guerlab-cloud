package net.guerlab.smart.platform.user.api.feign.factory;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.smart.platform.user.api.feign.FeignDepartmentApi;
import net.guerlab.smart.platform.user.core.domain.DepartmentDTO;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.ListObject;
import net.guerlab.web.result.Result;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 部门服务快速失败类构建工厂
 *
 * @author guer
 */
public class FeignDepartmentApiFallbackFactory implements FallbackFactory<FeignDepartmentApi> {

    @Override
    public FeignDepartmentApi create(Throwable cause) {
        return new FeignDepartmentApiFallback(cause);
    }

    /**
     * 部门服务快速失败实现
     */
    @Slf4j
    @AllArgsConstructor
    static class FeignDepartmentApiFallback implements FeignDepartmentApi {

        private final Throwable cause;

        @Override
        public Result<DepartmentDTO> findOne(Long departmentId) {
            log.error("findOne fallback", cause);
            return new Fail<>("fallback");
        }

        @Override
        public Result<ListObject<DepartmentDTO>> findList(Map<String, Object> searchParams) {
            log.error("findList fallback", cause);
            return new Fail<>("fallback", ListObject.empty());
        }

        @Override
        public Result<List<DepartmentDTO>> findAll(Map<String, Object> searchParams) {
            log.error("findAll fallback", cause);
            return new Fail<>("fallback", Collections.emptyList());
        }
    }
}
