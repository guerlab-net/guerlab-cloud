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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import net.guerlab.cloud.auth.TestConstants;
import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.domain.TestTokenInfo;
import net.guerlab.cloud.auth.properties.TestJwtTokenFactoryProperties;

/**
 * jwt token 工厂
 *
 * @author guer
 */
public class TestJwtTokenFactory extends AbstractJwtTokenFactory<ITestTokenInfo, TestJwtTokenFactoryProperties> {

    /**
     * 签名前缀
     */
    public static final String PREFIX = "TEST_JWT";

    @Override
    protected void generateToken0(JwtBuilder builder, ITestTokenInfo user) {
        builder.setSubject(user.getUsername());
        builder.claim(TestConstants.USER_ID, user.getUserId());
    }

    @Override
    protected ITestTokenInfo parse0(Claims body) {
        Long userId = Long.parseLong(getObjectValue(body.get(TestConstants.USER_ID)));
        String username = body.getSubject();

        return new TestTokenInfo(userId, username);
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

    @Override
    public Class<ITestTokenInfo> getAcceptClass() {
        return ITestTokenInfo.class;
    }
}
