package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户编辑信息
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户编辑信息")
public class UserModifyDTO extends UserDTO {

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;
}
