/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    @Schema(description = "男") MAN,

    /**
     * 女
     */
    @Schema(description = "女") WOMAN,

    /**
     * 其他
     */
    @Schema(description = "其他") OTHER
}
