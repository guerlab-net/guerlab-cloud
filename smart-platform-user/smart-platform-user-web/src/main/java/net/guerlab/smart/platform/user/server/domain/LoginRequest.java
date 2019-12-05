package net.guerlab.smart.platform.user.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录信息
 *
 * @author guer
 */
@Data
@ApiModel("登录信息")
public class LoginRequest {

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 双重因素认证密码
     */
    @ApiModelProperty("双重因素认证密码")
    private String otp;
}
