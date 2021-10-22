package net.guerlab.cloud.auth.webflux.test;

import net.guerlab.cloud.auth.webflux.filter.AbstractTokenHandlerFilter;
import net.guerlab.cloud.commons.ip.IpUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author guer
 */
public class TestTokenHandlerFilter extends AbstractTokenHandlerFilter<TestAuthWebProperties> {

    private final TestJwtTokenFactory tokenFactory;

    public TestTokenHandlerFilter(TestJwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    @Override
    protected boolean accept(String token, ServerHttpRequest request) {
        String ip = IpUtils.getIp(request);

        return tokenFactory.enabled() && tokenFactory.acceptAccessToken(token) && tokenFactory.acceptIp(ip);
    }

    @Override
    protected void setTokenInfo(String token) {
        TestUserInfo infoFromToken = tokenFactory.parseByAccessToken(token);

        TestContentHandler.setName(infoFromToken.getName());
    }
}
