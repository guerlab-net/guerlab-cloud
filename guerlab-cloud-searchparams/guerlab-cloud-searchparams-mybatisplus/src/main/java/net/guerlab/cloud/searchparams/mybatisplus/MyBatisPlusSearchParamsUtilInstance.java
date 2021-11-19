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
import net.guerlab.cloud.searchparams.AbstractSearchParamsUtilInstance;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * mybatis plus处理实例
 *
 * @author guer
 */
public class MyBatisPlusSearchParamsUtilInstance extends AbstractSearchParamsUtilInstance {

    private static final Class<?> CLAZZ = QueryWrapper.class;

    public MyBatisPlusSearchParamsUtilInstance() {
        setDefaultHandler(new DefaultHandler());

        StreamSupport.stream(ServiceLoader.load(AbstractMyBatisPlusSearchParamsHandler.class).spliterator(), false)
                .forEach((instance) -> addHandler(instance.acceptClass(), instance));
    }

    @Override
    public boolean accept(Object object) {
        return CLAZZ.isInstance(object);
    }
}
