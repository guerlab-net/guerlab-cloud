/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.smart.platform.auth.factory;

import net.guerlab.smart.platform.commons.exception.NotFoundCanUseTokenFactoryException;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * token工厂管理
 *
 * @author guer
 */
public class TokenFactoryManager {

    private TokenFactoryManager() {

    }

    /**
     * 获取token工厂列表
     *
     * @param entityClass
     *         实体类型
     * @param <T>
     *         实体类型
     * @return token工厂列表
     */
    public static <T> Collection<TokenFactory<T>> getTokenFactories(Class<T> entityClass) {
        return getTokenFactoriesByEntityClass0(entityClass).collect(Collectors.toList());
    }

    /**
     * 获取token工厂
     *
     * @param entityClass
     *         实体类型
     * @param <T>
     *         实体类型
     * @return token工厂
     */
    public static <T> TokenFactory<T> getDefaultTokenFactory(Class<T> entityClass) {
        List<TokenFactory<T>> factories = getTokenFactoriesByEntityClass0(entityClass).collect(Collectors.toList());

        if (factories.isEmpty()) {
            throw new NotFoundCanUseTokenFactoryException();
        }

        for (TokenFactory<T> factory : factories) {
            if (factory.isDefault()) {
                return factory;
            }
        }

        return factories.get(0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <T, F extends TokenFactory<T>> Stream<F> getTokenFactoriesByEntityClass0(Class<T> entityClass) {
        Collection<TokenFactory> factories = SpringApplicationContextUtil.getContext()
                .getBeansOfType(TokenFactory.class).values();

        if (factories.isEmpty()) {
            return Stream.empty();
        }

        Stream.Builder<F> streamBuilder = Stream.builder();

        for (TokenFactory<?> factory : factories) {
            Class<?> acceptClass = factory.getAcceptClass();

            if (acceptClass == null) {
                continue;
            }

            if (Objects.equals(entityClass, acceptClass) || entityClass.isAssignableFrom(acceptClass)) {
                streamBuilder.add((F) factory);
            }
        }

        return streamBuilder.build().filter(TokenFactory::enabled).sorted();
    }
}
