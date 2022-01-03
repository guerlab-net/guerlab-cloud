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
package net.guerlab.cloud.auth.factory;

import net.guerlab.cloud.auth.domain.TokenInfo;
import net.guerlab.cloud.auth.enums.TokenType;
import net.guerlab.cloud.auth.properties.StringValueTokenFactoryProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 抽象字符串类型值 token工厂
 *
 * @param <T>
 *         数据实体类型
 * @param <P>
 *         配置类型
 * @author guer
 */
public abstract class AbstractStringValueTokenFactory<T, P extends StringValueTokenFactoryProperties>
        extends AbstractTokenFactory<T, P> {

    /**
     * 有效期关键字
     */
    protected static final String EXPIRATION_KEY = "exp";

    /**
     * 起始日期关键字
     */
    protected static final String NOT_BEFORE_KEY = "nbf";

    /**
     * KV连接器
     */
    protected static final String KV_CONNECTORS = ":";

    /**
     * 分组连接器
     */
    protected static final String GROUP_CONNECTORS = "&";

    /**
     * 构造token
     *
     * @param prefix
     *         前缀
     * @param data
     *         数据
     * @param expire
     *         过期时间
     * @param key
     *         密钥
     * @return token信息
     */
    private TokenInfo build(String prefix, Map<String, String> data, long expire, String key) {
        long nowMillis = System.currentTimeMillis();
        Date exp = null;
        LocalDateTime expireAt = null;

        // 添加Token过期时间
        if (expire >= 0) {
            exp = new Date(nowMillis + expire);
            expireAt = LocalDateTime.ofInstant(exp.toInstant(), ZoneId.systemDefault());
            data.put(EXPIRATION_KEY, String.valueOf(nowMillis + expire));
            data.put(NOT_BEFORE_KEY, String.valueOf(nowMillis));
        }

        String dataString = data.entrySet().stream().map(entry -> entry.getKey() + KV_CONNECTORS + entry.getValue())
                .collect(Collectors.joining(GROUP_CONNECTORS));

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setExpire(exp == null ? -1 : expire);
        tokenInfo.setToken(prefix + buildToken(dataString, key, expire));
        if (expireAt != null) {
            tokenInfo.setExpireAt(expireAt);
        }

        return tokenInfo;
    }

    /**
     * 构造token
     *
     * @param dataString
     *         数据字符串
     * @param key
     *         密钥
     * @param expire
     *         过期时间
     * @return token
     */
    protected abstract String buildToken(String dataString, String key, long expire);

    /**
     * 解析token
     *
     * @param token
     *         token
     * @param key
     *         密钥
     * @param tokenType
     *         令牌类型
     * @return 数据
     */
    private Map<String, String> parserToken(String token, String key, TokenType tokenType) {
        String dataString = parseDataString(token, key);

        if (StringUtils.isBlank(dataString)) {
            throw tokenType.invalidException();
        }

        Map<String, String> data = new HashMap<>(8);

        for (String entry : dataString.split(GROUP_CONNECTORS)) {
            String[] keyValues = entry.split(KV_CONNECTORS);
            if (keyValues.length != 2) {
                continue;
            }
            data.put(keyValues[0], keyValues[1]);
        }

        long now = System.currentTimeMillis();

        if (data.containsKey(EXPIRATION_KEY)) {
            try {
                if (now > Long.parseLong(data.get(EXPIRATION_KEY))) {
                    throw tokenType.expiredException();
                }
            } catch (Exception e) {
                throw tokenType.expiredException();
            }
        }

        if (data.containsKey(NOT_BEFORE_KEY)) {
            try {
                if (now < Long.parseLong(data.get(NOT_BEFORE_KEY))) {
                    throw tokenType.expiredException();
                }
            } catch (Exception e) {
                throw tokenType.expiredException();
            }
        }

        data.remove(EXPIRATION_KEY);
        data.remove(NOT_BEFORE_KEY);

        return data;
    }

    /**
     * 解析数据字符串
     *
     * @param token
     *         token
     * @param key
     *         密钥
     * @return 数据字符串
     */
    @Nullable
    protected abstract String parseDataString(String token, String key);

    @Override
    public final TokenInfo generateByAccessToken(T entity) {
        Map<String, String> data = new HashMap<>(8);
        generateToken0(data, entity);

        return build(getAccessTokenPrefix(), data, properties.getAccessTokenExpire(), properties.getAccessTokenKey());
    }

    @Override
    public final TokenInfo generateByRefreshToken(T entity) {
        Map<String, String> data = new HashMap<>(8);
        generateToken0(data, entity);

        return build(getRefreshTokenPrefix(), data, properties.getRefreshTokenExpire(),
                properties.getRefreshTokenKey());
    }

    @Override
    public final T parseByAccessToken(String token) {
        String accessToken = token.substring(getAccessTokenPrefix().length());
        Map<String, String> body = parserToken(accessToken, properties.getAccessTokenKey(), TokenType.ACCESS_TOKEN);
        return parse0(body);
    }

    @Override
    public final T parseByRefreshToken(String token) {
        String refreshToken = token.substring(getRefreshTokenPrefix().length());
        Map<String, String> body = parserToken(refreshToken, properties.getRefreshTokenKey(), TokenType.REFRESH_TOKEN);
        return parse0(body);
    }

    /**
     * 解析token
     *
     * @param body
     *         token内容
     * @return 实体
     */
    protected abstract T parse0(Map<String, String> body);

    /**
     * 构建token
     *
     * @param data
     *         data
     * @param entity
     *         实体
     */
    protected abstract void generateToken0(Map<String, String> data, T entity);
}
