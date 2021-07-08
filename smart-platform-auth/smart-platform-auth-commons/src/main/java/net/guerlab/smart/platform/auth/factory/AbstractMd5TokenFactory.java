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

import net.guerlab.smart.platform.auth.domain.TokenInfo;
import net.guerlab.smart.platform.auth.enums.TokenType;
import net.guerlab.smart.platform.auth.properties.Md5TokenFactoryProperties;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 抽象jwt token工厂
 *
 * @author guer
 */
public abstract class AbstractMd5TokenFactory<T, P extends Md5TokenFactoryProperties>
        extends AbstractTokenFactory<T, P> {

    private static final String EXPIRATION_KEY = "exp";

    private static final String NOT_BEFORE_KEY = "nbf";

    private static final String KV_CONNECTORS = ":";

    private static final String GROUP_CONNECTORS = "&";

    private static final String TOKEN_CONNECTORS = ".";

    private static TokenInfo build(String prefix, Map<String, String> data, long expire, String signingKey) {
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

        String sign = DigestUtils.md5Hex(dataString + TOKEN_CONNECTORS + signingKey);

        String builder = Base64.getEncoder().encodeToString(dataString.getBytes()) + TOKEN_CONNECTORS + sign;

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setExpire(exp == null ? -1 : expire);
        tokenInfo.setExpireAt(expireAt);
        tokenInfo.setToken(prefix + builder);

        return tokenInfo;
    }

    private static Map<String, String> parserToken(String token, String signingKey, TokenType tokenType) {
        int index = token.lastIndexOf(TOKEN_CONNECTORS);

        if (index < 0) {
            throw tokenType.invalidException();
        }

        String dataString = new String(Base64.getDecoder().decode(token.substring(0, index)));
        String tokenSign = token.substring(index + 1);

        String sign = DigestUtils.md5Hex(dataString + TOKEN_CONNECTORS + signingKey);

        if (!Objects.equals(sign, tokenSign)) {
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

    @Override
    public final TokenInfo generateByAccessToken(T entity) {
        Map<String, String> data = new HashMap<>(8);
        generateToken0(data, entity);

        return build(getAccessTokenPrefix(), data, properties.getAccessTokenExpire(),
                properties.getAccessTokenSigningKey());
    }

    @Override
    public final TokenInfo generateByRefreshToken(T entity) {
        Map<String, String> data = new HashMap<>(8);
        generateToken0(data, entity);

        return build(getRefreshTokenPrefix(), data, properties.getRefreshTokenExpire(),
                properties.getRefreshTokenSigningKey());
    }

    @Override
    public final T parseByAccessToken(String token) {
        String accessToken = token.substring(getAccessTokenPrefix().length());
        Map<String, String> body = parserToken(accessToken, properties.getAccessTokenSigningKey(),
                TokenType.ACCESS_TOKEN);
        return parse0(body);
    }

    @Override
    public final T parseByRefreshToken(String token) {
        String refreshToken = token.substring(getRefreshTokenPrefix().length());
        Map<String, String> body = parserToken(refreshToken, properties.getRefreshTokenSigningKey(),
                TokenType.REFRESH_TOKEN);
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
