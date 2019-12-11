package net.guerlab.smart.platform.user.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改密码信息
 *
 * @author guer
 */
@Data
@ApiModel("修改密码信息")
public class UpdatePasswordDTO {

    /**
     * 旧密码
     */
    @ApiModelProperty("旧密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @ApiModelProperty("新密码")
    private String newPassword;
}
