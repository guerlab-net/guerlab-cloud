package net.guerlab.smart.platform.basic.auth.factory;

import net.guerlab.smart.platform.basic.auth.domain.TokenInfo;

/**
 * 抽象token工厂
 *
 * @param <T>
 *         数据实体类型
 * @author guer
 */
public interface TokenFactory<T> {

    /**
     * 链接符
     */
    String CONNECTORS = " ";

    /**
     * 是否已启用
     *
     * @return 已启用
     */
    boolean enabled();

    /**
     * 是否为默认token工厂
     *
     * @return 否为默认token工厂
     */
    boolean isDefault();

    /**
     * 获取access token前缀
     *
     * @return access token前缀
     */
    String getAccessTokenPrefix();

    /**
     * 获取refresh token前缀
     *
     * @return refresh token前缀
     */
    String getRefreshTokenPrefix();

    /**
     * 判断是否使用该token工厂
     *
     * @param token
     *         token
     * @return 是否使用
     */
    default boolean accept(String token) {
        return acceptAccessToken(token) || acceptRefreshToken(token);
    }

    /**
     * 判断是否使用该token工厂
     *
     * @param token
     *         token
     * @return 是否使用
     */
    default boolean acceptAccessToken(String token) {
        return token != null && token.startsWith(getAccessTokenPrefix());
    }

    /**
     * 判断是否使用该token工厂
     *
     * @param token
     *         token
     * @return 是否使用
     */
    default boolean acceptRefreshToken(String token) {
        return token != null && token.startsWith(getRefreshTokenPrefix());
    }

    /**
     * 获取可使用的对象类型
     *
     * @return 对象类型
     */
    Class<T> getAcceptClass();

    /**
     * 构造access token
     *
     * @param entity
     *         数据实体
     * @return token信息
     */
    TokenInfo generateByAccessToken(T entity);

    /**
     * 构造refresh token
     *
     * @param entity
     *         数据实体
     * @return token信息
     */
    TokenInfo generateByRefreshToken(T entity);

    /**
     * 根据access token解析数据实体
     *
     * @param token
     *         access token
     * @return 数据实体
     */
    T parseByAccessToken(String token);

    /**
     * 根据refresh token解析数据实体
     *
     * @param token
     *         refresh token
     * @return 数据实体
     */
    T parseByRefreshToken(String token);
}
