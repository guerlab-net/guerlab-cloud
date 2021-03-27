package net.guerlab.smart.platform.auth.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 令牌信息
 *
 * @author guer
 */
@Data
@Schema(name = "TokenInfo", description = "登录成功信息")
public class TokenInfo {

    /**
     * 令牌
     */
    @Schema(description = "令牌")
    private String token;

    /**
     * 过期时间
     */
    @Schema(description = "过期时间")
    private LocalDateTime expireAt;

    /**
     * 过期时长
     */
    @Schema(description = "过期时长")
    private Long expire;
}

