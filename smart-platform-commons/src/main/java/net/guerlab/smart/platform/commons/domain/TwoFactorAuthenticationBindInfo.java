package net.guerlab.smart.platform.commons.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 双重认证信息绑定资料
 *
 * @author guer
 */
@Data
@Schema(name = "双重认证信息绑定资料")
public class TwoFactorAuthenticationBindInfo {

    /**
     * 密钥
     */
    @Schema(name = "密钥", required = true)
    private String secretKey;

    /**
     * 二维码内容
     */
    @Schema(name = "二维码内容", required = true)
    private String qrBarcode;
}
