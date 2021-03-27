package net.guerlab.smart.platform.commons.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 性别
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Schema(name = "Gender", description = "性别")
public enum Gender {

    /**
     * 男
     */
    @Schema(description = "男")
    MAN,

    /**
     * 女
     */
    @Schema(description = "女")
    WOMAN,

    /**
     * 其他
     */
    @Schema(description = "其他")
    OTHER
}
