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
package net.guerlab.cloud.commons.searchparams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.OrderByIndex;
import net.guerlab.spring.searchparams.OrderByType;

import javax.persistence.Column;

/**
 * 可排序搜索参数
 *
 * @author guer
 */
@Getter
@Setter
@Schema(name = "OrderSearchParams", description = "可排序搜索参数")
public class OrderSearchParams extends AbstractSearchParams {

    /**
     * 排序值排序方式
     */
    @Schema(hidden = true)
    @JsonIgnore
    @Column(name = "orderNum")
    @OrderByIndex(-10)
    private OrderByType orderNumOrderByType = OrderByType.DESC;
}
