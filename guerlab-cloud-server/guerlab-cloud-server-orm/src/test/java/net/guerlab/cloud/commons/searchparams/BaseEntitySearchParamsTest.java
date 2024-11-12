/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.searchparams.BaseSearchParams;
import net.guerlab.cloud.searchparams.Column;
import net.guerlab.cloud.searchparams.OrderBy;
import net.guerlab.cloud.searchparams.OrderBys;
import net.guerlab.cloud.searchparams.SearchModel;
import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.SearchParamsUtils;

/**
 * @author guer
 */
class BaseEntitySearchParamsTest {

	@Test
	void test1() {
		OrderBys orderBys = new OrderBys();
		orderBys.add(new OrderBy("createdTime", true));

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setOrderBys(orderBys);
		searchParams.setCreatedBy("test");
		searchParams.setValue(1L);

		QueryWrapper<TestObj> queryWrapper = new QueryWrapper<>();
		queryWrapper.setEntityClass(TestObj.class);
		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals("(CREATED_BY = ? AND VALUE = ?) ORDER BY CREATED_TIME ASC",
				queryWrapper.getTargetSql());
	}

	@Data
	public static class TestObj implements IBaseEntity {

		/**
		 * 主键ID.
		 */
		@Schema(description = "主键ID")
		@TableId(value = EntityColumnNames.ID, type = IdType.ASSIGN_ID)
		protected Long id;

		/**
		 * 创建时间.
		 */
		@Schema(description = "创建时间")
		@TableField(value = EntityColumnNames.CREATED_TIME, fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
		private LocalDateTime createdTime;

		/**
		 * 最后修改时间.
		 */
		@Schema(description = "最后修改时间")
		@TableField(value = EntityColumnNames.LAST_UPDATED_TIME, fill = FieldFill.INSERT_UPDATE)
		private LocalDateTime lastUpdatedTime;

		/**
		 * 创建人.
		 */
		@Schema(description = "创建人")
		@TableField(value = EntityColumnNames.CREATED_BY, fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
		private String createdBy;

		/**
		 * 修改人.
		 */
		@Schema(description = "修改人")
		@TableField(value = EntityColumnNames.MODIFIED_BY, fill = FieldFill.INSERT_UPDATE)
		private String modifiedBy;

		@TableField("VALUE")
		private Long val;

		@Override
		public Long id() {
			return id;
		}

		@Override
		public void id(@Nullable Long id) {
			this.id = id;
		}
	}

	@Setter
	@Getter
	public static class TestSearchParams extends BaseSearchParams {

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

		@Column(name = "val")
		private Long value;
	}
}
