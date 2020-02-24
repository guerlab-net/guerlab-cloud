package net.guerlab.smart.platform.basic.auth.enums;

import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.commons.exception.AccessTokenExpiredException;
import net.guerlab.smart.platform.commons.exception.AccessTokenInvalidException;
import net.guerlab.smart.platform.commons.exception.RefreshTokenExpiredException;
import net.guerlab.smart.platform.commons.exception.RefreshTokenInvalidException;
import org.apache.commons.lang3.StringUtils;

/**
 * 令牌类型
 *
 * @author guer
 */
public enum TokenType {

    /**
     * AccessToken
     */
    ACCESS_TOKEN {
        @Override
        public String simpleName() {
            return SIMPLE_NAME_ACCESS_TOKEN;
        }

        @Override
        public ApplicationException expiredException() {
            return new AccessTokenExpiredException();
        }

        @Override
        public ApplicationException invalidException() {
            return new AccessTokenInvalidException();
        }
    },

    /**
     * RefreshToken
     */
    REFRESH_TOKEN {
        @Override
        public String simpleName() {
            return SIMPLE_NAME_REFRESH_TOKEN;
        }

        @Override
        public ApplicationException expiredException() {
            return new RefreshTokenExpiredException();
        }

        @Override
        public ApplicationException invalidException() {
            return new RefreshTokenInvalidException();
        }
    };

    public static final String SIMPLE_NAME_ACCESS_TOKEN = "AT";

    public static final String SIMPLE_NAME_REFRESH_TOKEN = "RT";

    /**
     * 通过简短名称获取令牌类型
     *
     * @param simpleName
     *         简短名称
     * @return 令牌类型
     */
    public static TokenType parseBySimpleName(String simpleName) {
        String name = StringUtils.trimToNull(simpleName);
        if (SIMPLE_NAME_ACCESS_TOKEN.equals(name)) {
            return ACCESS_TOKEN;
        } else if (SIMPLE_NAME_REFRESH_TOKEN.equals(name)) {
            return REFRESH_TOKEN;
        } else {
            return null;
        }
    }

    /**
     * 获取简短名称
     *
     * @return 简短名称
     */
    public abstract String simpleName();

    /**
     * 获取Token过期异常
     *
     * @return Token过期异常
     */
    public abstract ApplicationException expiredException();

    /**
     * 获取Token无效异常
     *
     * @return Token无效异常
     */
    public abstract ApplicationException invalidException();
}
