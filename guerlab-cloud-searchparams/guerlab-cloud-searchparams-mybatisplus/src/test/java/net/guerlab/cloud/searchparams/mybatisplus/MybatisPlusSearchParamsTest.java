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

		Assertions.assertEquals(
				"((JSON_SEARCH(t7, 'one', 'a', null, '$.a.b') IS NOT NULL OR JSON_SEARCH(t7, 'one', 'b', null, '$.a.b') IS NOT NULL))",
				queryWrapper.getSqlSegment());
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

		Assertions.assertEquals(
				"((json_exists(t7, '$.a.b?(@ == \"a\")') OR json_exists(t7, '$.a.b?(@ == \"b\")')))",
				queryWrapper.getSqlSegment());
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

		Assertions.assertEquals(
				"((JSON_SEARCH(t8, 'one', 'a', null, '$.a.b') IS NULL AND JSON_SEARCH(t8, 'one', 'b', null, '$.a.b') IS NULL))",
				queryWrapper.getSqlSegment());
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

		Assertions.assertEquals(
				"((json_exists(t8, '$.a.b?(!(@ == \"a\"))') AND json_exists(t8, '$.a.b?(!(@ == \"b\"))')))",
				queryWrapper.getSqlSegment());
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
				"(JSON_SEARCH(t9, 'one', '#{ew.paramNameValuePairs.MPGENVAL1}', null, '$.a.b') IS NOT NULL)",
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
				"(JSON_SEARCH(t10, 'one', '#{ew.paramNameValuePairs.MPGENVAL1}', null, '$.a.b') IS NULL)",
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
	@Order(15)
	void testWithBetweenInvalid() {
		TestSearchParams searchParams = new TestSearchParams();
		searchParams.setAgeBetween(Between.of(null, null));

		QueryWrapper<?> queryWrapper = new QueryWrapper<>();

		SearchParamsUtils.handler(searchParams, queryWrapper);

		Assertions.assertEquals("", queryWrapper.getSqlSegment());
	}
}
