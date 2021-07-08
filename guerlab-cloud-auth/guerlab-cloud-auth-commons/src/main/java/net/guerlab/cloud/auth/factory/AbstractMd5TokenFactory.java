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

import net.guerlab.cloud.auth.properties.Md5TokenFactoryProperties;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;
import java.util.Objects;

/**
 * 抽象md5 token工厂
 *
 * @author guer
 */
public abstract class AbstractMd5TokenFactory<T, P extends Md5TokenFactoryProperties>
        extends AbstractStringValueTokenFactory<T, P> {

    private static final String TOKEN_CONNECTORS = ".";

    @Override
    protected String buildToken(String dataString, String key, long expire) {
        String sign = DigestUtils.md5Hex(dataString + TOKEN_CONNECTORS + key);
        return Base64.getEncoder().encodeToString(dataString.getBytes()) + TOKEN_CONNECTORS + sign;
    }

    @Override
    protected String parseDataString(String token, String key) {
        int index = token.lastIndexOf(TOKEN_CONNECTORS);

        if (index < 0) {
            return null;
        }

        String dataString = new String(Base64.getDecoder().decode(token.substring(0, index)));
        String tokenSign = token.substring(index + 1);

        String sign = DigestUtils.md5Hex(dataString + TOKEN_CONNECTORS + key);

        if (!Objects.equals(sign, tokenSign)) {
            return null;
        }

        return dataString;
    }
}
