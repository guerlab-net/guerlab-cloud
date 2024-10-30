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

package net.guerlab.cloud.searchparams.mybatisplus;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import net.guerlab.cloud.searchparams.Between;
import net.guerlab.cloud.searchparams.SearchParamsUtils;

/**
 * @author guer
 */
@SuppressWarnings("SpellCheckingInspection")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MybatisPlusSearchParamsTest {

	@Test
	@Order(1)
	void test1() {
		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT1(Arrays.asList("a", "b"));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals("(('column' in #{ew.paramNameValuePairs.MPGENVAL1}))", queryWrapper.getSqlSegment());
	}

	@Test
	@Order(2)
	void test2() {
		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT2(Arrays.asList("a", "b"));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals(
				"(('column' in (#{ew.paramNameValuePairs.MPGENVAL1}, #{ew.paramNameValuePairs.MPGENVAL2})))",
				queryWrapper.getSqlSegment());
	}

	@Test
	@Order(3)
	void test3() {
		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT3(Arrays.asList("a", "b"));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals(
				"(('column' in #{ew.paramNameValuePairs.MPGENVAL1} and 'column' in #{ew.paramNameValuePairs.MPGENVAL1}))",
				queryWrapper.getSqlSegment());
	}

	@Test
	@Order(4)
	void test4() {
		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT4(Arrays.asList("a", "b"));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals(
				"(('column' in #{ew.paramNameValuePairs.MPGENVAL1} and 'column' in (#{ew.paramNameValuePairs.MPGENVAL1}, #{ew.paramNameValuePairs.MPGENVAL2})))",
				queryWrapper.getSqlSegment());
	}

	@Test
	@Order(5)
	void test5() {
		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT5(Arrays.asList("a", "b"));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals(
				"(('column' in (#{ew.paramNameValuePairs.MPGENVAL1}, #{ew.paramNameValuePairs.MPGENVAL2}) and 'column' in (#{ew.paramNameValuePairs.MPGENVAL1}, #{ew.paramNameValuePairs.MPGENVAL2})))",
				queryWrapper.getSqlSegment());
	}

	@Test
	@Order(6)
	void test6() {
		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT6(Arrays.asList("a", "b"));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals(
				"((_v = #{ew.paramNameValuePairs.MPGENVAL1} OR _v = #{ew.paramNameValuePairs.MPGENVAL2}))",
				queryWrapper.getSqlSegment());
	}

	@Test
	@Order(7)
	void test7WithMysql() {
		DbTypeUtils.removeProvider(TestOracleDbTypeProvider.class);
		DbTypeUtils.addProvider(new TestMysqlDbTypeProvider());

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT7(Arrays.asList("a", "b"));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		String targetSqlSegment = "(JSON_SEARCH(t7, 'one', #{ew.paramNameValuePairs.MPGENVAL1}, null, '$.a.b') IS NOT NULL OR JSON_SEARCH(t7, 'one', #{ew.paramNameValuePairs.MPGENVAL2}, null, '$.a.b') IS NOT NULL)";

		Assertions.assertEquals(targetSqlSegment, queryWrapper.getSqlSegment());
		Assertions.assertEquals("a", queryWrapper.getParamNameValuePairs().get("MPGENVAL1"));
		Assertions.assertEquals("b", queryWrapper.getParamNameValuePairs().get("MPGENVAL2"));
	}

	@Test
	@Order(8)
	void test7WithOracle() {
		DbTypeUtils.removeProvider(TestMysqlDbTypeProvider.class);
		DbTypeUtils.addProvider(new TestOracleDbTypeProvider());

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT7(Arrays.asList("a", "b"));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		String targetSqlSegment = "(json_exists(t7, #{ew.paramNameValuePairs.MPGENVAL1}) OR json_exists(t7, #{ew.paramNameValuePairs.MPGENVAL2}))";

		Assertions.assertEquals(targetSqlSegment, queryWrapper.getSqlSegment());
		Assertions.assertEquals("$.a.b?(@ == \"a\")", queryWrapper.getParamNameValuePairs().get("MPGENVAL1"));
		Assertions.assertEquals("$.a.b?(@ == \"b\")", queryWrapper.getParamNameValuePairs().get("MPGENVAL2"));
	}

	@Test
	@Order(9)
	void test8WithMysql() {
		DbTypeUtils.removeProvider(TestOracleDbTypeProvider.class);
		DbTypeUtils.addProvider(new TestMysqlDbTypeProvider());

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT8(Arrays.asList("a", "b"));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		String targetSqlSegment = "(JSON_SEARCH(t8, 'one', #{ew.paramNameValuePairs.MPGENVAL1}, null, '$.a.b') IS NULL AND JSON_SEARCH(t8, 'one', #{ew.paramNameValuePairs.MPGENVAL2}, null, '$.a.b') IS NULL)";

		Assertions.assertEquals(targetSqlSegment, queryWrapper.getSqlSegment());
		Assertions.assertEquals("a", queryWrapper.getParamNameValuePairs().get("MPGENVAL1"));
		Assertions.assertEquals("b", queryWrapper.getParamNameValuePairs().get("MPGENVAL2"));
	}

	@Test
	@Order(10)
	void test8WithOracle() {
		DbTypeUtils.removeProvider(TestMysqlDbTypeProvider.class);
		DbTypeUtils.addProvider(new TestOracleDbTypeProvider());

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT8(Arrays.asList("a", "b"));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		String targetSqlSegment = "(json_exists(t8, #{ew.paramNameValuePairs.MPGENVAL1}) AND json_exists(t8, #{ew.paramNameValuePairs.MPGENVAL2}))";

		Assertions.assertEquals(targetSqlSegment, queryWrapper.getSqlSegment());
		Assertions.assertEquals("$.a.b?(!(@ == \"a\"))", queryWrapper.getParamNameValuePairs().get("MPGENVAL1"));
		Assertions.assertEquals("$.a.b?(!(@ == \"b\"))", queryWrapper.getParamNameValuePairs().get("MPGENVAL2"));
	}


	@Test
	@Order(11)
	void test9WithMysql() {
		DbTypeUtils.removeProvider(TestOracleDbTypeProvider.class);
		DbTypeUtils.addProvider(new TestMysqlDbTypeProvider());

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT9("a");

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals(
				"(JSON_SEARCH(t9, 'one', #{ew.paramNameValuePairs.MPGENVAL1}, null, '$.a.b') IS NOT NULL)",
				queryWrapper.getSqlSegment());
	}

	@Test
	@Order(12)
	void test9WithOracle() {
		DbTypeUtils.removeProvider(TestMysqlDbTypeProvider.class);
		DbTypeUtils.addProvider(new TestOracleDbTypeProvider());

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT9("a");

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals(
				"(json_exists(t9, #{ew.paramNameValuePairs.MPGENVAL1}))",
				queryWrapper.getSqlSegment());
	}

	@Test
	@Order(13)
	void test10WithMysql() {
		DbTypeUtils.removeProvider(TestOracleDbTypeProvider.class);
		DbTypeUtils.addProvider(new TestMysqlDbTypeProvider());

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT10("a");

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals(
				"(JSON_SEARCH(t10, 'one', #{ew.paramNameValuePairs.MPGENVAL1}, null, '$.a.b') IS NULL)",
				queryWrapper.getSqlSegment());
	}

	@Test
	@Order(14)
	void test10WithOracle() {
		DbTypeUtils.removeProvider(TestMysqlDbTypeProvider.class);
		DbTypeUtils.addProvider(new TestOracleDbTypeProvider());

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setT10("a");

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals(
				"(json_exists(t10, #{ew.paramNameValuePairs.MPGENVAL1}))",
				queryWrapper.getSqlSegment());
	}

	@Test
	@Order(15)
	void testWithBetweenValid() {
		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setAgeBetween(Between.of(1L, 2L));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals("(age BETWEEN #{ew.paramNameValuePairs.MPGENVAL1} AND #{ew.paramNameValuePairs.MPGENVAL2})", queryWrapper.getSqlSegment());
	}

	@Test
	@Order(16)
	void testWithBetweenInvalid() {
		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setAgeBetween(Between.of(null, null));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals("", queryWrapper.getSqlSegment());
	}

	@Test
	@Order(17)
	void testWithBigList() {
		CollectionHelper.setBatchSize(3);

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setBigList(List.of(1, 2, 3, 4));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals("((ID IN (#{ew.paramNameValuePairs.MPGENVAL1},#{ew.paramNameValuePairs.MPGENVAL2},#{ew.paramNameValuePairs.MPGENVAL3}) OR ID IN (#{ew.paramNameValuePairs.MPGENVAL4})))", queryWrapper.getSqlSegment());
	}

	@Test
	@Order(18)
	void testWithNotBigList() {
		CollectionHelper.setBatchSize(3);

		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setNotBigList(List.of(1, 2, 3, 4));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals("(ID NOT IN (#{ew.paramNameValuePairs.MPGENVAL1},#{ew.paramNameValuePairs.MPGENVAL2},#{ew.paramNameValuePairs.MPGENVAL3}) AND ID NOT IN (#{ew.paramNameValuePairs.MPGENVAL4}))", queryWrapper.getSqlSegment());
	}
}
