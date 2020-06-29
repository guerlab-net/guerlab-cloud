package net.guerlab.smart.platform.basic.auth.token;

import io.jsonwebtoken.*;
import net.guerlab.smart.platform.basic.auth.domain.TokenInfo;
import net.guerlab.smart.platform.basic.auth.enums.TokenType;
import net.guerlab.smart.platform.basic.auth.properties.JwtTokenFactoryProperties;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    private static TokenInfo build(String prefix, TokenType tokenType, JwtBuilder builder, long expire,
            String signingKey) {
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
        Key key = new SecretKeySpec(createKey(signingKey), signatureAlgorithm.getJcaName());
        builder.signWith(signatureAlgorithm, key);

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setExpire(exp == null ? -1 : expire);
        tokenInfo.setExpireAt(expireAt);
        tokenInfo.setToken(prefix + CONNECTORS + tokenType.simpleName() + CONNECTORS + builder.compact());

        return tokenInfo;
    }

    private static Jws<Claims> parserToken(String token, String signingKey, TokenType tokenType) {
        try {
            return Jwts.parser().setSigningKey(createKey(signingKey)).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw tokenType.expiredException();
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            throw tokenType.invalidException();
        }
    }

    private static byte[] createKey(String key) {
        return DatatypeConverter.parseBase64Binary(key == null ? "" : key);
    }

    @Override
    public final TokenInfo generateByAccessToken(T entity) {
        JwtBuilder builder = builder();
        generateToken0(builder, entity);

        return build(getPrefix(), TokenType.ACCESS_TOKEN, builder, properties.getAccessTokenExpire(),
                properties.getAccessTokenSigningKey());
    }

    @Override
    public final TokenInfo generateByRefreshToken(T entity) {
        JwtBuilder builder = builder();
        generateToken0(builder, entity);

        return build(getPrefix(), TokenType.REFRESH_TOKEN, builder, properties.getRefreshTokenExpire(),
                properties.getRefreshTokenSigningKey());
    }

    @Override
    public final T parseByAccessToken(String token) {
        Claims body = parserToken(token, properties.getAccessTokenSigningKey(), TokenType.ACCESS_TOKEN).getBody();
        return parse0(body);
    }

    @Override
    public final T parseByRefreshToken(String token) {
        Claims body = parserToken(token, properties.getRefreshTokenSigningKey(), TokenType.REFRESH_TOKEN).getBody();
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
