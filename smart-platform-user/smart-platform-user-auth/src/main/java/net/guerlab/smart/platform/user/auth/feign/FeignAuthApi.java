package net.guerlab.smart.platform.user.auth.feign;

import net.guerlab.smart.platform.user.auth.feign.factory.FeignAuthApiFallbackFactory;
import net.guerlab.smart.platform.user.core.entity.OtpCheckRequest;
import net.guerlab.smart.platform.user.core.entity.OtpCheckResponse;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckRequest;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckResponse;
import net.guerlab.web.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * feign的权限检查处理接口
 *
 * @author guer
 */
@FeignClient(name = "user-api/inside/auth/", fallbackFactory = FeignAuthApiFallbackFactory.class)
public interface FeignAuthApi {

    /**
     * 授权检查
     *
     * @param request
     *         权限请求信息
     * @return 权限检查结果信息
     */
    @PostMapping("permissionCheck")
    Result<PermissionCheckResponse> permissionCheck(@RequestBody PermissionCheckRequest request);

    /**
     * 双因子认证检查
     *
     * @param request
     *         双因子认证请求信息
     * @return 双因子认证检查结果信息
     */
    @PostMapping("otpCheck")
    Result<OtpCheckResponse> otpCheck(@RequestBody OtpCheckRequest request);
}
