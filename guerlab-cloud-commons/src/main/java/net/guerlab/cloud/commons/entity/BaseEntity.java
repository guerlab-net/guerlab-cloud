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
package net.guerlab.cloud.commons.entity;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础实体
 *
 * @author guer
 */
@Setter
@Getter
@Schema(name = "BaseEntity", description = "基础实体")
public abstract class BaseEntity {

    /**
     * 乐观锁版本
     */
    @Version
    @Schema(description = "乐观锁版本", accessMode = Schema.AccessMode.READ_ONLY)
    protected Long version;
}