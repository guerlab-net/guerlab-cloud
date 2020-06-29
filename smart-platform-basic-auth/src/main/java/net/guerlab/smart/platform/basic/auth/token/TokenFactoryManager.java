package net.guerlab.smart.platform.basic.auth.token;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;

import java.util.Collection;
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
    public static <T, F extends TokenFactory<T>> Collection<F> getTokenFactories(Class<F> factoryClass) {
        return getTokenFactories0(factoryClass).collect(Collectors.toList());
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
    public static <T, F extends TokenFactory<T>> Collection<F> getEnabledTokenFactories(Class<F> factoryClass) {
        return getTokenFactories0(factoryClass).filter(TokenFactory::enabled).collect(Collectors.toList());
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
    public static <T, F extends TokenFactory<T>> F getDefaultTokenFactory(Class<F> factoryClass) {
        return getTokenFactories0(factoryClass).filter(TokenFactory::enabled).filter(TokenFactory::isDefault)
                .findFirst().orElseThrow(() -> new ApplicationException(
                        "TokenFactory not found with type " + factoryClass.getTypeName()));
    }

    private static <T, F extends TokenFactory<T>> Stream<F> getTokenFactories0(Class<F> factoryClass) {
        Collection<F> factories = SpringApplicationContextUtil.getContext().getBeansOfType(factoryClass).values();
        return factories.stream().filter(Objects::nonNull);
    }
}
