/*
 * Copyright 2018-2021 guerlab.net and other contributors.
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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 抽象通用搜索参数对象
 *
 * @author guer
 */
@Setter
@Getter
@Schema(name = "AbstractSearchParams", description = "抽象通用搜索参数对象")
public abstract class AbstractSearchParams {

    /**
     * 分页ID
     */
    @Schema(name = "pageId", description = "分页ID", defaultValue = "1")
    protected int pageId = 1;

    /**
     * 分页内容数量
     */
    @Schema(name = "pageId", description = "分页内容数量", defaultValue = "10")
    protected int pageSize = 10;
}
