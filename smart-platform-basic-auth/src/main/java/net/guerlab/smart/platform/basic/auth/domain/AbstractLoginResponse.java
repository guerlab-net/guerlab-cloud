package net.guerlab.smart.platform.basic.auth.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 抽象登录成功信息
 *
 * @author guer
 */
@Data
@ApiModel("抽象登录成功信息")
public class AbstractLoginResponse<T> {

    /**
     * accessToken
     */
    @ApiModelProperty("accessToken")
    protected TokenInfo accessToken;

    /**
     * refreshToken
     */
    @ApiModelProperty("refreshToken")
    protected TokenInfo refreshToken;

    /**
     * 用户信息
     */
    @ApiModelProperty("用户信息")
    protected T info;
}
