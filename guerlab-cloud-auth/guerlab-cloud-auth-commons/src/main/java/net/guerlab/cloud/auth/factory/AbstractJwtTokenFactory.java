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

import io.jsonwebtoken.*;
import net.guerlab.cloud.auth.domain.TokenInfo;
import net.guerlab.cloud.auth.enums.TokenType;
import net.guerlab.cloud.auth.properties.JwtTokenFactoryProperties;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * 抽象jwt token工厂
 *
 * @author guer
 */
public abstract class AbstractJwtTokenFactory<T, P extends JwtTokenFactoryProperties>
        extends AbstractTokenFactory<T, P> {

    private static JwtBuilder builder() {
        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ", "JWT");

        return builder;
    }

    private static TokenInfo build(String prefix, JwtBuilder builder, long expire, String keyString) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = null;
        LocalDateTime expireAt = null;

        // 添加Token过期时间
        if (expire >= 0) {
            exp = new Date(nowMillis + expire);
            expireAt = LocalDateTime.ofInstant(exp.toInstant(), ZoneId.systemDefault());
            builder.setExpiration(exp).setNotBefore(now);
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key key = new SecretKeySpec(createKey(keyString), signatureAlgorithm.getJcaName());
        builder.signWith(signatureAlgorithm, key);

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setExpire(exp == null ? -1 : expire);
        tokenInfo.setExpireAt(expireAt);
        tokenInfo.setToken(prefix + builder.compact());

        return tokenInfo;
    }

    private static Jws<Claims> parserToken(String token, String key, TokenType tokenType) {
        try {
            return Jwts.parser().setSigningKey(createKey(key)).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw tokenType.expiredException();
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            throw tokenType.invalidException();
        }
    }

    private static byte[] createKey(String key) {
        return Base64.getEncoder().encode(key.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public final TokenInfo generateByAccessToken(T entity) {
        JwtBuilder builder = builder();
        generateToken0(builder, entity);

        return build(getAccessTokenPrefix(), builder, properties.getAccessTokenExpire(),
                properties.getAccessTokenKey());
    }

    @Override
    public final TokenInfo generateByRefreshToken(T entity) {
        JwtBuilder builder = builder();
        generateToken0(builder, entity);

        return build(getRefreshTokenPrefix(), builder, properties.getRefreshTokenExpire(),
                properties.getAccessTokenKey());
    }

    @Override
    public final T parseByAccessToken(String token) {
        String accessToken = token.substring(getAccessTokenPrefix().length());
        Claims body = parserToken(accessToken, properties.getAccessTokenKey(), TokenType.ACCESS_TOKEN).getBody();
        return parse0(body);
    }

    @Override
    public final T parseByRefreshToken(String token) {
        String refreshToken = token.substring(getRefreshTokenPrefix().length());
        Claims body = parserToken(refreshToken, properties.getAccessTokenKey(), TokenType.REFRESH_TOKEN).getBody();
        return parse0(body);
    }

    /**
     * 解析token
     *
     * @param body
     *         token内容
     * @return 实体
     */
    protected abstract T parse0(Claims body);

    /**
     * 构建token
     *
     * @param builder
     *         jwtBuilder
     * @param entity
     *         实体
     */
    protected abstract void generateToken0(JwtBuilder builder, T entity);
}
