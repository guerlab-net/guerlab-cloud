package net.guerlab.smart.platform.user.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.basic.auth.domain.AbstractLoginResponse;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.entity.UserOauthDTO;

import java.util.Collection;

/**
 * 登录成功信息
 *
 * @author guer
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("登录成功信息")
public class LoginResponse extends AbstractLoginResponse<UserDTO> {

    /**
     * 权限关键字列表
     */
    @ApiModelProperty("权限关键字列表")
    private Collection<String> permissionKeys;

    /**
     * 用户oauth信息
     */
    @ApiModelProperty("用户oauth信息")
    private UserOauthDTO thirdParty;

}
