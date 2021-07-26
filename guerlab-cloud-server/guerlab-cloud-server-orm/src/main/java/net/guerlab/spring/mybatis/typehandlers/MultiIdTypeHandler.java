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
import net.guerlab.cloud.commons.domain.MultiId;
import net.guerlab.spring.mybatis.AbstractTypeHandler;

/**
 * ID集合类型处理
 *
 * @author guer
 */
@SuppressWarnings({ "WeakerAccess", "unused" })
public class MultiIdTypeHandler extends AbstractTypeHandler<MultiId> {

    @Override
    protected TypeReference<MultiId> getTypeReference() {
        return new TypeReference<>() {};
    }
}
