package net.guerlab.smart.platform.basic.auth.token;

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
