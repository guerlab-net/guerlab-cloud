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

package net.guerlab.cloud.searchparams.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.guerlab.cloud.searchparams.SearchParamsUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author guer
 */
@SuppressWarnings("SpellCheckingInspection")
class MybatisPlusSearchParamsTest {

    @Test
    void test1() {
        TestSearchParams searchParams = new TestSearchParams();
        searchParams.setT1(Arrays.asList("a", "b"));

        QueryWrapper<?> queryWrapper = new QueryWrapper<>();

        SearchParamsUtils.handler(searchParams, queryWrapper);

        Assertions.assertEquals("(('column' in #{ew.paramNameValuePairs.MPGENVAL1}))", queryWrapper.getSqlSegment());
    }

    @Test
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
    void test5() {
        TestSearchParams searchParams = new TestSearchParams();
        searchParams.setT5(Arrays.asList("a", "b"));

        QueryWrapper<?> queryWrapper = new QueryWrapper<>();

        SearchParamsUtils.handler(searchParams, queryWrapper);

        Assertions.assertEquals(
                "(('column' in (#{ew.paramNameValuePairs.MPGENVAL1}, #{ew.paramNameValuePairs.MPGENVAL2}) and 'column' in (#{ew.paramNameValuePairs.MPGENVAL1}, #{ew.paramNameValuePairs.MPGENVAL2})))",
                queryWrapper.getSqlSegment());
    }
}
