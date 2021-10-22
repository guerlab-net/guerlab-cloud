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
package net.guerlab.cloud.auth.webmvc.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import net.guerlab.cloud.auth.factory.AbstractJwtTokenFactory;

/**
 * jwt token 工厂
 *
 * @author guer
 */
public class TestJwtTokenFactory extends AbstractJwtTokenFactory<TestUserInfo, TestJwtTokenFactoryProperties> {

    /**
     * 签名前缀
     */
    public static final String PREFIX = "TEST_JWT";

    @Override
    protected void generateToken0(JwtBuilder builder, TestUserInfo user) {
        builder.setSubject(user.getName());
    }

    @Override
    protected TestUserInfo parse0(Claims body) {
        String username = body.getSubject();

        return new TestUserInfo(username);
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }

    @Override
    public Class<TestUserInfo> getAcceptClass() {
        return TestUserInfo.class;
    }
}
