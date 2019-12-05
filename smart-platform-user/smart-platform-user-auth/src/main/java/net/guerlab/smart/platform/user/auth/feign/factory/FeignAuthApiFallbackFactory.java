package net.guerlab.smart.platform.user.auth.feign.factory;

import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.guerlab.smart.platform.user.auth.feign.FeignAuthApi;
import net.guerlab.smart.platform.user.core.entity.OtpCheckRequest;
import net.guerlab.smart.platform.user.core.entity.OtpCheckResponse;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckRequest;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckResponse;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.Result;

/**
 * @author guer
 */
public class FeignAuthApiFallbackFactory implements FallbackFactory<FeignAuthApi> {

    @Override
    public FeignAuthApi create(Throwable cause) {
        return new FeignDepartmentApiFallback(cause);
    }

    @Slf4j
    @AllArgsConstructor
    static class FeignDepartmentApiFallback implements FeignAuthApi {

        private final Throwable cause;

        @Override
        public Result<PermissionCheckResponse> permissionCheck(PermissionCheckRequest request) {
            log.error("permissionCheck fallback", cause);
            return new Fail<>("fallback");
        }

        @Override
        public Result<OtpCheckResponse> otpCheck(OtpCheckRequest request) {
            log.error("otpCheck fallback", cause);
            return new Fail<>("fallback");
        }
    }
}
