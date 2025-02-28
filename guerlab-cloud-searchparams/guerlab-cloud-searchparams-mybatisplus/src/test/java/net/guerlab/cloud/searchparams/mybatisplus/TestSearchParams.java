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

package net.guerlab.cloud.searchparams.mybatisplus;

import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import net.guerlab.cloud.searchparams.Between;
import net.guerlab.cloud.searchparams.Column;
import net.guerlab.cloud.searchparams.JsonField;
import net.guerlab.cloud.searchparams.SearchModel;
import net.guerlab.cloud.searchparams.SearchModelType;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.searchparams.SubQuerySupport;

/**
 * @author guer
 */
@Setter
@Getter
@SuppressWarnings("unused")
public class TestSearchParams implements SearchParams {

	@SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "'column' in ?")
	private Collection<String> t1;

	@SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "'column' in ?*")
	private Collection<String> t2;

	@SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "'column' in ? and 'column' in ?")
	private Collection<String> t3;

	@SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "'column' in ? and 'column' in ?*")
	private Collection<String> t4;

	@SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "'column' in ?* and 'column' in ?*")
	private Collection<String> t5;

	@SearchModel(sqlProviders = TestSqlProvider.class)
	private Collection<String> t6;

	@JsonField(jsonPath = "$.a.b")
	private Collection<String> t7;

	@JsonField(jsonPath = "$.a.b")
	@SearchModel(SearchModelType.NOT_IN)
	private Collection<String> t8;

	@JsonField(jsonPath = "$.a.b")
	private String t9;

	@JsonField(jsonPath = "$.a.b")
	@SearchModel(SearchModelType.NOT_IN)
	private String t10;

	@Column(name = "age")
	@SearchModel(SearchModelType.BETWEEN)
	private Between<Long> ageBetween;

	@Column(name = "ID")
	@SearchModel(SearchModelType.IN)
	private List<Integer> bigList;

	@Column(name = "ID")
	@SearchModel(SearchModelType.NOT_IN)
	private List<Integer> notBigList;

	@Column(name = "ID")
	@SubQuerySupport(resultFieldName = "ID", entityClass = TestSubEntity.class, tableName = "TEST_SUB_ENTITY")
	private TestSubSearchParams subSearchParams;

	@Column(name = "ID")
	@SubQuerySupport(resultFieldName = "ID", entityClass = TestSubEntity.class, tableName = "TEST_SUB_ENTITY", baseWhere = "NAME = 'ABC'")
	private TestSubSearchParams subSearchParamsWithBaseWhere;
}
