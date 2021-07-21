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
package net.guerlab.spring.mybatis.typehandlers;

import com.fasterxml.jackson.core.type.TypeReference;
import net.guerlab.cloud.commons.domain.MultiString;
import net.guerlab.spring.mybatis.AbstractTypeHandler;

/**
 * String集合类型处理
 *
 * @author guer
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class MultiStringTypeHandler extends AbstractTypeHandler<MultiString> {

    @Override
    protected TypeReference<MultiString> getTypeReference() {
        return new TypeReference<>() {};
    }
}
