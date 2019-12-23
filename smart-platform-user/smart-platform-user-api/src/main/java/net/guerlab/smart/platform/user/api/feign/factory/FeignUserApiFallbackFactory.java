package net.guerlab.smart.platform.user.api.feign.factory;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.smart.platform.user.api.feign.FeignUserApi;
import net.guerlab.smart.platform.user.core.domain.PositionDataDTO;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.domain.UserModifyDTO;
import net.guerlab.smart.platform.user.core.utils.PositionUtils;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.ListObject;
import net.guerlab.web.result.Result;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        @Override
        public Result<List<String>> permissionKeys(Long userId) {
            log.error("permissionKeys fallback", cause);
            return new Fail<>("fallback", Collections.emptyList());
        }

        @Override
        public Result<List<PositionDataDTO>> getPosition(Long userId) {
            log.error("getPosition fallback", cause);
            return new Fail<>("fallback", Collections.emptyList());
        }

        @Override
        public Result<Set<String>> getPositionKeys(Long userId) {
            log.error("getPositionKeys fallback", cause);
            return new Fail<>("fallback", Collections.singleton(PositionUtils.ALL_DEPARTMENT_POSITION));
        }

        @Override
        public Result<UserDTO> add(UserModifyDTO user) {
            log.error("add fallback", cause);
            return new Fail<>("fallback");
        }
    }
}
