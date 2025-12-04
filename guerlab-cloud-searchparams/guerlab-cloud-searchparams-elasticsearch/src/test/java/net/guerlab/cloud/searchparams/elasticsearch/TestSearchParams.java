/*
 * Copyright 2018-2026 guerlab.net and other contributors.
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

package net.guerlab.cloud.searchparams.elasticsearch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import net.guerlab.cloud.searchparams.BaseSearchParams;
import net.guerlab.cloud.searchparams.Column;
import net.guerlab.cloud.searchparams.SearchModel;
import net.guerlab.cloud.searchparams.SearchModelType;

/**
 * @author guer
 */
@Setter
@Getter
@SuppressWarnings("unused")
public class TestSearchParams extends BaseSearchParams {

	@SearchModel(SearchModelType.IS_NULL)
	@Column(name = "IS_NULL")
	@Field(type = FieldType.Text)
	private String isNull = "isNull";

	@SearchModel(SearchModelType.IS_NOT_NULL)
	@Column(name = "IS_NOT_NULL")
	private String isNotNull = "isNotNull";

	@SearchModel(SearchModelType.EQUAL_TO)
	@Column(name = "EQUAL_TO")
	private String equalTo = "equalTo";

	@SearchModel(SearchModelType.NOT_EQUAL_TO)
	@Column(name = "NOT_EQUAL_TO")
	private String notEqualTo = "notEqualTo";

	@SearchModel(SearchModelType.GREATER_THAN)
	@Column(name = "GREATER_THAN")
	private Long greaterThan = 1L;

	@SearchModel(SearchModelType.GREATER_THAN_OR_EQUAL_TO)
	@Column(name = "GREATER_THAN_OR_EQUAL_TO")
	private LocalDateTime greaterThanOrEqualTo = LocalDateTime.of(2023, 5, 20, 12, 34, 56);

	@SearchModel(SearchModelType.LESS_THAN)
	@Column(name = "LESS_THAN")
	private LocalTime lessThan = LocalTime.of(12, 34, 56);

	@SearchModel(SearchModelType.LESS_THAN_OR_EQUAL_TO)
	@Column(name = "LESS_THAN_OR_EQUAL_TO")
	private LocalDate lessThanOrEqualTo = LocalDate.of(2023, 5, 20);

	@SearchModel(SearchModelType.LIKE)
	@Column(name = "LIKE")
	private String like = "like";

	@SearchModel(SearchModelType.NOT_LIKE)
	@Column(name = "NOT_LIKE")
	private String notLike = "notLike";

	@SearchModel(SearchModelType.START_WITH)
	@Column(name = "START_WITH")
	private String startWith = "startWith";

	@SearchModel(SearchModelType.START_NOT_WITH)
	@Column(name = "START_NOT_WITH")
	private String startNotWith = "startNotWith";

	@SearchModel(SearchModelType.END_WITH)
	@Column(name = "END_WITH")
	private String endWith = "endWith";

	@SearchModel(SearchModelType.END_NOT_WITH)
	@Column(name = "END_NOT_WITH")
	private String endNotWith = "endNotWith";

	@SearchModel(SearchModelType.IN)
	@Column(name = "IN")
	private List<String> in = Arrays.asList("v1", "v2");

	@SearchModel(SearchModelType.NOT_IN)
	@Column(name = "NOT_IN")
	private List<String> notIn = Arrays.asList("v1", "v2");
}
