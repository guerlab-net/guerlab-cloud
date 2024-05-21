/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

import java.util.List;
import java.util.TimeZone;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;

import net.guerlab.cloud.searchparams.OrderBy;
import net.guerlab.cloud.searchparams.OrderBys;
import net.guerlab.cloud.searchparams.SearchParamsUtils;

/**
 * @author guer
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ElasticSearchSearchParamsTest {

	@Test
	@Order(0)
	void sortTest() {
		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setOrderBys(OrderBys.of(new OrderBy("t1"), new OrderBy("t2", false)));

		NativeQueryBuilder builder = new NativeQueryBuilder();

		SearchParamsUtils.handler(searchParams, builder);

		List<SortOptions> sort = builder.build().getSortOptions();

		Assertions.assertNotNull(sort);
		Assertions.assertEquals("[SortOptions: {\"t1\":{\"order\":\"asc\"}}, SortOptions: {\"t2\":{\"order\":\"desc\"}}]", sort.toString());
	}

	@Test
	@Order(1)
	void nativeQuery() {
		BoolQueryBuilderDefaultHandler.setDefaultTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		TestSearchParams searchParams = new TestSearchParams();

		BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

		SearchParamsUtils.handler(searchParams, boolQueryBuilder);

		NativeQueryBuilder builder = new NativeQueryBuilder();
		builder.withQuery(boolQueryBuilder.build()._toQuery());

		Query query = builder.build().getQuery();

		Assertions.assertNotNull(query);
		Assertions.assertEquals("Query: {\"bool\":{\"must\":[{\"exists\":{\"field\":\"isNotNull\"}},{\"term\":{\"equalTo\":{\"value\":\"equalTo\"}}},{\"range\":{\"greaterThan\":{\"gt\":1}}},{\"range\":{\"greaterThanOrEqualTo\":{\"gte\":\"2023-05-20T12:34:56\",\"time_zone\":\"GMT+08:00\"}}},{\"range\":{\"lessThan\":{\"lt\":\"12:34:56\",\"time_zone\":\"GMT+08:00\"}}},{\"range\":{\"lessThanOrEqualTo\":{\"lte\":\"2023-05-20\",\"time_zone\":\"GMT+08:00\"}}},{\"wildcard\":{\"like\":{\"value\":\"*like*\"}}},{\"wildcard\":{\"startWith\":{\"value\":\"startWith*\"}}},{\"wildcard\":{\"endWith\":{\"value\":\"*endWith\"}}},{\"match\":{\"in\":{\"query\":\"v1\"}}},{\"match\":{\"in\":{\"query\":\"v2\"}}}],\"must_not\":[{\"exists\":{\"field\":\"isNull\"}},{\"term\":{\"notEqualTo\":{\"value\":\"notEqualTo\"}}},{\"wildcard\":{\"notLike\":{\"value\":\"*notLike*\"}}},{\"wildcard\":{\"startNotWith\":{\"value\":\"startNotWith*\"}}},{\"wildcard\":{\"endNotWith\":{\"value\":\"*endNotWith\"}}},{\"match\":{\"notIn\":{\"query\":\"v1\"}}},{\"match\":{\"notIn\":{\"query\":\"v2\"}}}]}}", query.toString());
	}
}
