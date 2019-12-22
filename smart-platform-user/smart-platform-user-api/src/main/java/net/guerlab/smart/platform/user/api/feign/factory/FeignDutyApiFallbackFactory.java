package net.guerlab.smart.platform.user.api.feign.factory;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.smart.platform.user.api.feign.FeignDutyApi;
import net.guerlab.smart.platform.user.core.domain.DutyDTO;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.ListObject;
import net.guerlab.web.result.Result;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 职务服务快速失败类构建工厂
 *
 * @author guer
 */
public class FeignDutyApiFallbackFactory implements FallbackFactory<FeignDutyApi> {

    @Override
    public FeignDutyApi create(Throwable cause) {
        return new FeignDutyApiFallback(cause);
    }

    /**
     * 职务服务快速失败实现
     */
    @Slf4j
    @AllArgsConstructor
    static class FeignDutyApiFallback implements FeignDutyApi {

        private final Throwable cause;

        @Override
        public Result<DutyDTO> findOne(Long dutyId) {
            log.error("findOne fallback", cause);
            return new Fail<>("fallback");
        }

        @Override
        public Result<ListObject<DutyDTO>> findList(Map<String, Object> searchParams) {
            log.error("findList fallback", cause);
            return new Fail<>("fallback", ListObject.empty());
        }

        @Override
        public Result<List<DutyDTO>> findAll(Map<String, Object> searchParams) {
            log.error("findAll fallback", cause);
            return new Fail<>("fallback", Collections.emptyList());
        }
    }
}
