package net.guerlab.smart.platform.user.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 绑定信息
 *
 * @author guer
 */
@Data
@ApiModel("绑定信息")
public class BindRequest {

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
     * 第三方ID
     */
    @ApiModelProperty("第三方ID")
    private String thirdPartyId;
}
