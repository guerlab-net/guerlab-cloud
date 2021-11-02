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
package net.guerlab.cloud.auth.webflux.test;

import net.guerlab.cloud.auth.webflux.filter.AbstractTokenHandlerFilter;
import net.guerlab.cloud.commons.ip.IpUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author guer
 */
public class TestTokenHandlerFilter extends AbstractTokenHandlerFilter<TestAuthWebProperties> {

    private final TestJwtTokenFactory tokenFactory;

    public TestTokenHandlerFilter(TestAuthWebProperties authProperties, TestJwtTokenFactory tokenFactory) {
        super(authProperties);
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
