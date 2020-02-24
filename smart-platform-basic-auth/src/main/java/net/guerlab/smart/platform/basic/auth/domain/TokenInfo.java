package net.guerlab.smart.platform.basic.auth.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 令牌信息
 *
 * @author guer
 */
@Data
@ApiModel("登录成功信息")
public class TokenInfo {

    /**
     * 令牌
     */
    @ApiModelProperty("令牌")
    private String token;

    /**
     * 过期时间
     */
    @ApiModelProperty("过期时间")
    private LocalDateTime expireAt;

    /**
     * 过期时长
     */
    @ApiModelProperty("过期时长")
    private Long expire;
}

