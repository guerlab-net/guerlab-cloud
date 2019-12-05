package net.guerlab.smart.platform.user.api.feign.factory;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.smart.platform.user.api.feign.FeignUserApi;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.ListObject;
import net.guerlab.web.result.Result;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 用户服务快速失败类构建工厂
 *
 * @author guer
 */
public class FeignUserApiFallbackFactory implements FallbackFactory<FeignUserApi> {

    @Override
    public FeignUserApi create(Throwable cause) {
        return new FeignUserApiFallback(cause);
    }

    /**
     * 用户服务快速失败实现
     */
    @Slf4j
    @AllArgsConstructor
    static class FeignUserApiFallback implements FeignUserApi {

        private final Throwable cause;

        @Override
        public Result<UserDTO> findOne(Long userId) {
            log.error("findOne fallback", cause);
            return new Fail<>("fallback");
        }

        @Override
        public Result<ListObject<UserDTO>> findList(Map<String, Object> searchParams) {
            log.error("findList fallback", cause);
            return new Fail<>("fallback", ListObject.empty());
        }

        @Override
        public Result<List<UserDTO>> findAll(Map<String, Object> searchParams) {
            log.error("findAll fallback", cause);
            return new Fail<>("fallback", Collections.emptyList());
        }
    }
}
