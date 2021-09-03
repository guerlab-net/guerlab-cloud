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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.cloud.auth.domain.TokenInfo;
import net.guerlab.cloud.auth.enums.TokenType;
import net.guerlab.cloud.auth.properties.RedisTokenFactoryProperties;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.commons.random.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 抽象redis token工厂
 *
 * @author guer
 */
public abstract class AbstractRedisTokenFactory<T, P extends RedisTokenFactoryProperties>
        extends AbstractTokenFactory<T, P> {

    protected RedisTemplate<String, String> redisTemplate;

    protected ObjectMapper objectMapper;

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
        String data;
        try {
            data = objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }
        while (true) {
            key = prefix + RandomUtil.nextString(keyLength);
            if (!Objects
                    .equals(redisTemplate.opsForValue().setIfAbsent(key, data, expire, TimeUnit.MILLISECONDS), true)) {
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
        String data = redisTemplate.opsForValue().get(token);
        if (data == null) {
            throw tokenType.invalidException();
        }
        try {
            return objectMapper.readValue(data, getTypeReference());
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 获取数据对象类型引用
     *
     * @return 数据对象类型引用
     */
    protected abstract TypeReference<? extends T> getTypeReference();

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
