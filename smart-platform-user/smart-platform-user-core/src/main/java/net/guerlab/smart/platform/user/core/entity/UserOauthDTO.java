package net.guerlab.smart.platform.user.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户oauth信息
 *
 * @author guer
 */
@Data
@ApiModel("用户oauth信息")
public class UserOauthDTO {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private String type;

    /**
     * 第三方ID
     */
    @ApiModelProperty("第三方ID")
    private String thirdPartyId;
}
