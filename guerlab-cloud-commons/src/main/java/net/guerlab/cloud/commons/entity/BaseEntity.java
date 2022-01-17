/*
 * Copyright 2018-2022 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.commons.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体
 *
 * @author guer
 */
@Data
@Schema(name = "BaseEntity", description = "基础实体")
public abstract class BaseEntity implements Serializable {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = EntityColumnNames.ID, type = IdType.ASSIGN_ID)
    protected Long id;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(value = EntityColumnNames.CREATED_TIME, fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createdTime;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间")
    @TableField(value = EntityColumnNames.LAST_UPDATED_TIME, fill = FieldFill.INSERT)
    private LocalDateTime lastUpdatedTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @TableField(value = EntityColumnNames.CREATED_BY, fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private String createdBy;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    @TableField(value = EntityColumnNames.MODIFIED_BY, fill = FieldFill.INSERT)
    private String modifiedBy;

    /**
     * 逻辑删除标识
     */
    @Schema(hidden = true)
    @JsonIgnore
    @TableLogic
    @TableField(value = EntityColumnNames.DELETED, fill = FieldFill.INSERT)
    private Boolean deleted;

    /**
     * 乐观锁版本
     */
    @Schema(hidden = true)
    @JsonIgnore
    @Version
    @TableField(value = EntityColumnNames.VERSION)
    private Long version;
}
