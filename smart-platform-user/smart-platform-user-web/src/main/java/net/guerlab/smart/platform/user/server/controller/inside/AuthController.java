package net.guerlab.smart.platform.user.server.controller.inside;

import net.guerlab.smart.platform.user.core.entity.OtpCheckRequest;
import net.guerlab.smart.platform.user.core.entity.OtpCheckResponse;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckRequest;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckResponse;
import net.guerlab.smart.platform.user.server.service.OtpCheckService;
import net.guerlab.smart.platform.user.server.service.PermissionCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权接口
 *
 * @author guer
 */
@RestController("/inside/auth")
@RequestMapping("/inside/auth")
public class AuthController {

    private PermissionCheckService permissionCheckService;

    private OtpCheckService otpCheckService;

    /**
     * 权限检查
     *
     * @param request
     *         请求
     * @return 响应
     */
    @PostMapping("/permissionCheck")
    public PermissionCheckResponse permissionCheck(@RequestBody PermissionCheckRequest request) {
        return permissionCheckService.acceptByPermissionKeys(request.getUserId(), request.getPermissionKeys());
    }

    /**
     * 双因子认证检查
     *
     * @param request
     *         请求
     * @return 响应
     */
    @PostMapping("/otpCheck")
    public OtpCheckResponse otpCheck(@RequestBody OtpCheckRequest request) {
        return otpCheckService.otpCheck(request);
    }

    @Autowired
    public void setPermissionCheckService(PermissionCheckService permissionCheckService) {
        this.permissionCheckService = permissionCheckService;
    }

    @Autowired
    public void setOtpCheckService(OtpCheckService otpCheckService) {
        this.otpCheckService = otpCheckService;
    }
}
