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
package net.guerlab.cloud.searchparams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 可排序搜索参数
 *
 * @author guer
 */
@Getter
@Setter
@Schema(name = "OrderlySearchParams", description = "可排序搜索参数")
public class OrderlySearchParams implements SearchParams {

    /**
     * 排序值排序方式
     */
    @Schema(hidden = true)
    @JsonIgnore
    @Column(name = "orderNum")
    @OrderByIndex(-10)
    private OrderByType orderNumOrderByType = OrderByType.DESC;
}
