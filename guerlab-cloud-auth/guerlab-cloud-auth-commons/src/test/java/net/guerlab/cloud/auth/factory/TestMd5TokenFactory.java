/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

import java.util.Map;

import net.guerlab.cloud.auth.TestConstants;
import net.guerlab.cloud.auth.domain.ITestTokenInfo;
import net.guerlab.cloud.auth.domain.TestTokenInfo;
import net.guerlab.cloud.auth.properties.TestMd5TokenFactoryProperties;

/**
 * md5 token 工厂.
 *
 * @author guer
 */
public class TestMd5TokenFactory extends AbstractMd5TokenFactory<ITestTokenInfo, TestMd5TokenFactoryProperties> {

	/**
	 * 签名前缀.
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
