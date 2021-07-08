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
package net.guerlab.cloud.auth.test.factory;

import net.guerlab.cloud.auth.factory.AbstractMd5TokenFactory;
import net.guerlab.cloud.auth.test.TestConstants;
import net.guerlab.cloud.auth.test.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.test.domain.TestTokenInfo;
import net.guerlab.cloud.auth.test.properties.TestMd5TokenFactoryProperties;

import java.util.Map;

/**
 * md5 token 工厂
 *
 * @author guer
 */
public class TestMd5TokenFactory extends AbstractMd5TokenFactory<ITestTokenInfo, TestMd5TokenFactoryProperties> {

    /**
     * 签名前缀
     */
    public static final String PREFIX = "TEST_MD5";

    @Override
    protected void generateToken0(Map<String, String> map, ITestTokenInfo user) {
        map.put(TestConstants.USERNAME, user.getUsername());
        map.put(TestConstants.USER_ID, user.getUserId().toString());
    }

    @Override
    protected ITestTokenInfo parse0(Map<String, String> map) {
        Long userId = Long.parseLong(getObjectValue(map.get(TestConstants.USER_ID)));
        String username = getObjectValue(map.get(TestConstants.USERNAME));

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
