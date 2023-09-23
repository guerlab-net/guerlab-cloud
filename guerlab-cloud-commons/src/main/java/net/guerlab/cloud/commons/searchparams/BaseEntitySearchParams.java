/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

package net.guerlab.cloud.commons.searchparams;

import java.time.LocalDateTime;
import java.util.Collection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import net.guerlab.cloud.commons.entity.EntityColumnNames;
import net.guerlab.cloud.searchparams.BaseSearchParams;
import net.guerlab.cloud.searchparams.Column;
import net.guerlab.cloud.searchparams.SearchModel;
import net.guerlab.cloud.searchparams.SearchModelType;

/**
 * 基础对象搜索参数.
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(name = "BaseEntitySearchParams", description = "基础对象搜索参数")
public abstract class BaseEntitySearchParams extends BaseSearchParams {

	/**
	 * 主键ID.
	 */
	@Schema(description = "主键ID")
	@Column(name = EntityColumnNames.ID)
	private Long id;

	/**
	 * 主键ID列表.
	 */
	@Schema(description = "主键ID列表")
	@Column(name = EntityColumnNames.ID)
	@SearchModel(SearchModelType.IN)
	private Collection<Long> ids;

	/**
	 * 主键ID不等于.
	 */
	@Schema(description = "主键ID不等于")
	@Column(name = EntityColumnNames.ID)
	@SearchModel(SearchModelType.NOT_EQUAL_TO)
	private Long notId;

	/**
	 * 主键ID不包含列表.
	 */
	@Schema(description = "主键ID不包含列表")
	@Column(name = EntityColumnNames.ID)
	@SearchModel(SearchModelType.NOT_IN)
	private Collection<Long> notIds;

	/**
	 * 创建时间开始范围.
	 */
	@Schema(description = "创建时间开始范围")
	@Column(name = EntityColumnNames.CREATED_TIME)
	@SearchModel(SearchModelType.GREATER_THAN_OR_EQUAL_TO)
	private LocalDateTime createdTimeStartWith;

	/**
	 * 创建时间结束范围.
	 */
	@Schema(description = "创建时间结束范围")
	@Column(name = EntityColumnNames.CREATED_TIME)
	@SearchModel(SearchModelType.LESS_THAN_OR_EQUAL_TO)
	private LocalDateTime createdTimeEndWith;

	/**
	 * 最后修改时间开始范围.
	 */
	@Schema(description = "最后修改时间开始范围")
	@Column(name = EntityColumnNames.LAST_UPDATED_TIME)
	@SearchModel(SearchModelType.GREATER_THAN_OR_EQUAL_TO)
	private LocalDateTime lastUpdatedTimeStartWith;

	/**
	 * 最后修改时间结束范围.
	 */
	@Schema(description = "最后修改时间结束范围")
	@Column(name = EntityColumnNames.LAST_UPDATED_TIME)
	@SearchModel(SearchModelType.LESS_THAN_OR_EQUAL_TO)
	private LocalDateTime lastUpdatedTimeEndWith;

	/**
	 * 创建人.
	 */
	@Schema(description = "创建人")
	@Column(name = EntityColumnNames.CREATED_BY)
	private String createdBy;

	/**
	 * 创建人列表.
	 */
	@Schema(description = "创建人列表")
	@Column(name = EntityColumnNames.CREATED_BY)
	@SearchModel(SearchModelType.IN)
	private Collection<String> createdBys;

	/**
	 * 修改人.
	 */
	@Schema(description = "修改人")
	@Column(name = EntityColumnNames.CREATED_BY)
	private String modifiedBy;

	/**
	 * 修改人列表.
	 */
	@Schema(description = "修改人列表")
	@Column(name = EntityColumnNames.CREATED_BY)
	@SearchModel(SearchModelType.IN)
	private Collection<String> modifiedBys;
}
