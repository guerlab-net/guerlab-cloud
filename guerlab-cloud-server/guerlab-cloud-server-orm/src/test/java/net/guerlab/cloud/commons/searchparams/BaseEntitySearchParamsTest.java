/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.guerlab.cloud.commons.entity.BaseEntity;
import net.guerlab.cloud.searchparams.Column;
import net.guerlab.cloud.searchparams.OrderBy;
import net.guerlab.cloud.searchparams.OrderBys;
import net.guerlab.cloud.searchparams.SearchParamsUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author guer
 */
public class BaseEntitySearchParamsTest {

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

        Assertions.assertEquals("(VALUE = ? AND CREATED_BY = ?) ORDER BY CREATED_TIME ASC",
                queryWrapper.getTargetSql());
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class TestObj extends BaseEntity {

        @TableField(value = "VALUE")
        private Long val;
    }

    @Setter
    @Getter
    public static class TestSearchParams extends BaseEntitySearchParams {

        @Column(name = "val")
        private Long value;
    }
}
