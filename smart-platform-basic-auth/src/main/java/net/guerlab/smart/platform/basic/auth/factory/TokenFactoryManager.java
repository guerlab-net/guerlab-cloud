package net.guerlab.smart.platform.basic.auth.factory;

import net.guerlab.commons.exception.ApplicationException;
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
     * @param factoryClass
     *         token工厂类型
     * @param <T>
     *         实体类型
     * @param <F>
     *         token工厂类型
     * @return token工厂列表
     */
    public static <T, F extends TokenFactory<T>> Collection<F> getTokenFactoriesByFactoryClass(Class<F> factoryClass) {
        return getTokenFactoriesByFactoryClass0(factoryClass).collect(Collectors.toList());
    }

    /**
     * 获取已启用的token工厂列表
     *
     * @param factoryClass
     *         token工厂类型
     * @param <T>
     *         实体类型
     * @param <F>
     *         token工厂类型
     * @return 已启用的token工厂列表
     */
    public static <T, F extends TokenFactory<T>> Collection<F> getEnabledTokenFactoriesByFactoryClass(
            Class<F> factoryClass) {
        return getTokenFactoriesByFactoryClass0(factoryClass).filter(TokenFactory::enabled)
                .collect(Collectors.toList());
    }

    /**
     * 获取token工厂
     *
     * @param factoryClass
     *         token工厂类型
     * @param <T>
     *         实体类型
     * @param <F>
     *         token工厂类型
     * @return token工厂
     */
    public static <T, F extends TokenFactory<T>> F getDefaultTokenFactoryByFactoryClass(Class<F> factoryClass) {
        List<F> factories = getTokenFactoriesByFactoryClass0(factoryClass).filter(TokenFactory::enabled)
                .collect(Collectors.toList());

        if (factories.isEmpty()) {
            throw new ApplicationException("TokenFactory not found with factory type " + factoryClass.getTypeName());
        }

        for (F factory : factories) {
            if (factory.isDefault()) {
                return factory;
            }
        }

        return factories.get(0);
    }

    private static <T, F extends TokenFactory<T>> Stream<F> getTokenFactoriesByFactoryClass0(Class<F> factoryClass) {
        Collection<F> factories = SpringApplicationContextUtil.getContext().getBeansOfType(factoryClass).values();
        return factories.stream().filter(Objects::nonNull);
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
    public static <T> Collection<TokenFactory<T>> getTokenFactoriesByEntityClass(Class<T> entityClass) {
        return getTokenFactoriesByEntityClass0(entityClass).collect(Collectors.toList());
    }

    /**
     * 获取已启用的token工厂列表
     *
     * @param entityClass
     *         实体类型
     * @param <T>
     *         实体类型
     * @return 已启用的token工厂列表
     */
    public static <T> Collection<TokenFactory<T>> getEnabledTokenFactoriesByEntityClass(Class<T> entityClass) {
        return getTokenFactoriesByEntityClass0(entityClass).filter(TokenFactory::enabled).collect(Collectors.toList());
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
    public static <T> TokenFactory<T> getDefaultTokenFactoryByEntityClass(Class<T> entityClass) {
        List<TokenFactory<T>> factories = getTokenFactoriesByEntityClass0(entityClass).filter(TokenFactory::enabled)
                .collect(Collectors.toList());

        if (factories.isEmpty()) {
            throw new ApplicationException("TokenFactory not found with entity type " + entityClass.getTypeName());
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

        return streamBuilder.build();
    }
}
