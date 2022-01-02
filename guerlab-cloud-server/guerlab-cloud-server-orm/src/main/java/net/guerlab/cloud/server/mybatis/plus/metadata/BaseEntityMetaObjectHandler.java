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

package net.guerlab.cloud.server.mybatis.plus.metadata;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import net.guerlab.cloud.auth.context.AbstractContextHandler;
import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.commons.entity.IOrderlyEntity;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 基础对象元信息处理
 *
 * @author guer
 */
public class BaseEntityMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createdTime = Optional.ofNullable(metaObject.getValue("createdTime")).orElse(LocalDateTime.now());
        setFieldValByName("createdTime", createdTime, metaObject);
        setFieldValByName("lastUpdatedTime", createdTime, metaObject);
        setFieldValByName("deleted", false, metaObject);

        Object createdBy = Optional.ofNullable(metaObject.getValue("createdBy"))
                .orElse(AbstractContextHandler.getCurrentOperator());
        setFieldValByName("createdBy", createdBy, metaObject);
        setFieldValByName("modifiedBy", createdBy, metaObject);

        if (metaObject.getOriginalObject() instanceof IOrderlyEntity<?>) {
            IOrderlyEntity<?> obj = (IOrderlyEntity<?>) metaObject.getOriginalObject();

            if (obj.getOrderNum() == null) {
                obj.setOrderNum(Constants.DEFAULT_ORDER_NUM);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object modifiedBy = AbstractContextHandler.getCurrentOperator();
        if (modifiedBy == null) {
            modifiedBy = metaObject.getValue("modifiedBy");
        }
        setFieldValByName("modifiedBy", modifiedBy, metaObject);
        setFieldValByName("lastUpdatedTime", LocalDateTime.now(), metaObject);
    }
}
