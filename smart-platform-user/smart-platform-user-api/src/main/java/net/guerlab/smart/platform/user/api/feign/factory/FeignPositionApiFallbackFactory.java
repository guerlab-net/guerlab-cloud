package net.guerlab.smart.platform.user.api.feign.factory;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.smart.platform.user.api.feign.FeignPositionApi;
import net.guerlab.smart.platform.user.core.domain.PositionDTO;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.ListObject;
import net.guerlab.web.result.Result;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 职位服务快速失败类构建工厂
 *
 * @author guer
 */
public class FeignPositionApiFallbackFactory implements FallbackFactory<FeignPositionApi> {

    @Override
    public FeignPositionApi create(Throwable cause) {
        return new FeignPositionApiFallback(cause);
    }

    /**
     * 职位服务快速失败实现
     */
    @Slf4j
    @AllArgsConstructor
    static class FeignPositionApiFallback implements FeignPositionApi {

        private final Throwable cause;

        @Override
        public Result<PositionDTO> findOne(Long positionId) {
            log.error("findOne fallback", cause);
            return new Fail<>("fallback");
        }

        @Override
        public Result<ListObject<PositionDTO>> findList(Map<String, Object> searchParams) {
            log.error("findList fallback", cause);
            return new Fail<>("fallback", ListObject.empty());
        }

        @Override
        public Result<List<PositionDTO>> findAll(Map<String, Object> searchParams) {
            log.error("findAll fallback", cause);
            return new Fail<>("fallback", Collections.emptyList());
        }
    }
}
