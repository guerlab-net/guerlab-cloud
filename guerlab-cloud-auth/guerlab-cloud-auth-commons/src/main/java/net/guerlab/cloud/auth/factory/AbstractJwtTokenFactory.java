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

import io.jsonwebtoken.*;
import net.guerlab.cloud.auth.domain.TokenInfo;
import net.guerlab.cloud.auth.enums.TokenType;
import net.guerlab.cloud.auth.properties.JwtTokenFactoryProperties;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 抽象jwt token工厂
 *
 * @param <T>
 *         数据实体类型
 * @param <P>
 *         配置类型
 * @author guer
 */
public abstract class AbstractJwtTokenFactory<T, P extends JwtTokenFactoryProperties>
        extends AbstractTokenFactory<T, P> {

    /**
     * 获取JwtBuilder
     *
     * @return JwtBuilder
     */
    private static JwtBuilder builder() {
        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ", "JWT");

        return builder;
    }

    /**
     * 构造token信息
     *
     * @param prefix
     *         token前缀
     * @param builder
     *         JwtBuilder
     * @param expire
     *         有效期
     * @param privateKey
     *         私钥
     * @return token信息
     */
    private static TokenInfo build(String prefix, JwtBuilder builder, long expire, PrivateKey privateKey) {
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

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        builder.signWith(signatureAlgorithm, privateKey);

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setExpire(exp == null ? -1 : expire);
        tokenInfo.setToken(prefix + builder.compact());
        if (expireAt != null) {
            tokenInfo.setExpireAt(expireAt);
        }

        return tokenInfo;
    }

    /**
     * 解析token
     *
     * @param token
     *         token
     * @param publicKey
     *         公钥
     * @param tokenType
     *         token类型
     * @return 解析后信息
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey, TokenType tokenType) {
        try {
            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw tokenType.expiredException();
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            throw tokenType.invalidException();
        }
    }

    @Override
    public final TokenInfo generateByAccessToken(T entity) {
        JwtBuilder builder = builder();
        generateToken0(builder, entity);

        return build(getAccessTokenPrefix(), builder, properties.getAccessTokenExpire(),
                properties.getAccessTokenKey().getPrivateKeyRef());
    }

    @Override
    public final TokenInfo generateByRefreshToken(T entity) {
        JwtBuilder builder = builder();
        generateToken0(builder, entity);

        return build(getRefreshTokenPrefix(), builder, properties.getRefreshTokenExpire(),
                properties.getRefreshTokenKey().getPrivateKeyRef());
    }

    @Override
    public final T parseByAccessToken(String token) {
        String accessToken = token.substring(getAccessTokenPrefix().length());
        Claims body = parserToken(accessToken, properties.getAccessTokenKey().getPublicKeyRef(), TokenType.ACCESS_TOKEN)
                .getBody();
        return parse0(body);
    }

    @Override
    public final T parseByRefreshToken(String token) {
        String refreshToken = token.substring(getRefreshTokenPrefix().length());
        Claims body = parserToken(refreshToken, properties.getRefreshTokenKey().getPublicKeyRef(),
                TokenType.REFRESH_TOKEN).getBody();
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
