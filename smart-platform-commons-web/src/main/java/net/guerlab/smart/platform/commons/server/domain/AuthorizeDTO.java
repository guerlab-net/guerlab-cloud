package net.guerlab.smart.platform.commons.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 授权信息
 *
 * @author guer
 */
@Data
@ApiModel("授权信息")
public class AuthorizeDTO {

    /**
     * 重定向路径
     */
    @ApiModelProperty("重定向路径")
    private String redirect;
}
