package net.guerlab.smart.platform.commons.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 双重认证信息绑定资料
 *
 * @author guer
 */
@Data
@ApiModel("双重认证信息绑定资料")
public class TwoFactorAuthenticationBindInfo {

    /**
     * 密钥
     */
    @ApiModelProperty("密钥")
    private String secretKey;

    /**
     * 二维码内容
     */
    @ApiModelProperty("二维码内容")
    private String qrBarcode;
}
