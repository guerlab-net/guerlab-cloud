package net.guerlab.smart.platform.auth.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 抽象登录成功信息
 *
 * @author guer
 */
@Data
@Schema(name = "AbstractLoginResponse", description = "抽象登录成功信息")
public class AbstractLoginResponse<T> {

    /**
     * accessToken
     */
    @Schema(description = "accessToken")
    protected TokenInfo accessToken;

    /**
     * refreshToken
     */
    @Schema(description = "refreshToken")
    protected TokenInfo refreshToken;

    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    protected T info;
}
