package net.guerlab.cloud.auth.webmvc.test;

import net.guerlab.cloud.auth.webmvc.interceptor.AbstractTokenHandlerInterceptor;
import net.guerlab.cloud.commons.ip.IpUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guer
 */
public class TestTokenHandlerInterceptor extends AbstractTokenHandlerInterceptor<TestAuthWebProperties> {

    private final TestJwtTokenFactory tokenFactory;

    public TestTokenHandlerInterceptor(TestJwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    @Override
    protected boolean accept(String token, HttpServletRequest request) {
        String ip = IpUtils.getIp(request);

        return tokenFactory.enabled() && tokenFactory.acceptAccessToken(token) && tokenFactory.acceptIp(ip);
    }

    @Override
    protected void setTokenInfo(String token) {
        TestUserInfo infoFromToken = tokenFactory.parseByAccessToken(token);

        TestContentHandler.setName(infoFromToken.getName());
    }
}
