package net.guerlab.smart.platform.basic.auth.interceptor;

import net.guerlab.smart.platform.basic.auth.properties.AuthProperties;
import net.guerlab.smart.platform.commons.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * 抽象token处理
 *
 * @param <A>
 *         授权配置类型
 * @author guer
 */
public abstract class AbstractTokenHandlerInterceptor<A extends AuthProperties> extends AbstractHandlerInterceptor {

    /**
     * 授权配置
     */
    protected A authProperties;

    @Override
    protected void preHandle0(HttpServletRequest request, HandlerMethod handlerMethod) {
        String token = StringUtils.trim(getToken(request));

        if (StringUtils.isNotBlank(token) && accept(token)) {
            setTokenInfo(token);
        }
    }

    /**
     * 判断是否处理该token
     *
     * @param token
     *         token
     * @return 是否处理该token
     */
    protected abstract boolean accept(String token);

    /**
     * 设置Token信息
     *
     * @param token
     *         token
     */
    protected abstract void setTokenInfo(String token);

    /**
     * 获取token
     *
     * @param request
     *         http请求对象
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN);

        if (StringUtils.isNotBlank(token)) {
            return token;
        }

        if (request.getCookies() != null) {
            Optional<Cookie> optional = Arrays.stream(request.getCookies())
                    .filter(cookie -> Constants.TOKEN.equals(cookie.getName())).findFirst();

            if (optional.isPresent()) {
                return optional.get().getValue();
            }
        }

        return request.getParameter(Constants.TOKEN);
    }

    /**
     * 获取授权配置
     *
     * @return 授权配置
     */
    public A getAuthProperties() {
        return authProperties;
    }

    /**
     * 设置授权配置
     *
     * @param authProperties
     *         授权配置
     */
    @Autowired
    public void setAuthProperties(A authProperties) {
        this.authProperties = authProperties;
    }
}
