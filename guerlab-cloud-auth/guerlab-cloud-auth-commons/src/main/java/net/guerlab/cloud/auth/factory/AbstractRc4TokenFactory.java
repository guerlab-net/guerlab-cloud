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
package net.guerlab.cloud.auth.factory;

import net.guerlab.cloud.auth.properties.Rc4TokenFactoryProperties;
import net.guerlab.commons.encrypt.AuthCodeHelper;

/**
 * 抽象rc4 token工厂
 *
 * @param <T>
 *         数据实体类型
 * @param <P>
 *         配置类型
 * @author guer
 */
public abstract class AbstractRc4TokenFactory<T, P extends Rc4TokenFactoryProperties>
        extends AbstractStringValueTokenFactory<T, P> {

    @Override
    protected String buildToken(String dataString, String key, long expire) {
        return AuthCodeHelper.encode(dataString, key, expire);
    }

    @Override
    protected String parseDataString(String token, String key) {
        return AuthCodeHelper.decode(token, key);
    }
}
