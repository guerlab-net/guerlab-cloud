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
package net.guerlab.cloud.auth.factory;

import net.guerlab.cloud.auth.domain.TokenInfo;
import net.guerlab.cloud.auth.enums.TokenType;
import net.guerlab.cloud.auth.properties.RedisTokenFactoryProperties;
import net.guerlab.cloud.auth.redis.RedisOperationsWrapper;
import net.guerlab.commons.random.RandomUtil;

import java.time.LocalDateTime;

/**
 * 抽象redis token工厂
 *
 * @param <T>
 *         数据实体类型
 * @param <P>
 *         配置类型
 * @author guer
 */
public abstract class AbstractRedisTokenFactory<T, P extends RedisTokenFactoryProperties>
        extends AbstractTokenFactory<T, P> {

    protected final RedisOperationsWrapper<T> redisOperationsWrapper;

    public AbstractRedisTokenFactory(RedisOperationsWrapper<T> redisOperationsWrapper) {
        this.redisOperationsWrapper = redisOperationsWrapper;
    }

    @Override
    public final TokenInfo generateByAccessToken(T entity) {
        return build(getAccessTokenPrefix(), entity, properties.getAccessTokenKeyLength(),
                properties.getAccessTokenExpire());
    }

    @Override
    public final TokenInfo generateByRefreshToken(T entity) {
        return build(getRefreshTokenPrefix(), entity, properties.getRefreshTokenKeyLength(),
                properties.getRefreshTokenExpire());
    }

    private TokenInfo build(String prefix, T entity, int keyLength, long expire) {
        String key;
        while (true) {
            key = prefix + RandomUtil.nextString(keyLength);
            if (!redisOperationsWrapper.put(key, entity, expire)) {
                continue;
            }

            LocalDateTime expireAt = null;

            // 添加Token过期时间
            if (expire >= 0) {
                expireAt = LocalDateTime.now().plusSeconds(expire);
            }

            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setExpire(expireAt == null ? -1 : expire);
            tokenInfo.setExpireAt(expireAt);
            tokenInfo.setToken(key);

            return tokenInfo;
        }
    }

    @Override
    public final T parseByAccessToken(String token) {
        return parse0(token, TokenType.ACCESS_TOKEN);
    }

    @Override
    public final T parseByRefreshToken(String token) {
        return parse0(token, TokenType.REFRESH_TOKEN);
    }

    private T parse0(String token, TokenType tokenType) {
        T entity = redisOperationsWrapper.get(token);
        if (entity == null) {
            throw tokenType.invalidException();
        }
        return entity;
    }
}
