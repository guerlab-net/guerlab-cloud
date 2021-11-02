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
package net.guerlab.cloud.commons.util;

import net.guerlab.cloud.commons.Constants;
import net.guerlab.cloud.commons.entity.IOrderEntity;
import org.springframework.lang.Nullable;

/**
 * 可排序对象工具类
 *
 * @author guer
 */
@SuppressWarnings("unused")
public class OrderEntityUtils {

    private OrderEntityUtils() {

    }

    /**
     * 属性检查，当排序值为null时设置为默认值
     *
     * @param entity
     *         可排序对象
     */
    public static void propertiesCheck(@Nullable IOrderEntity<?> entity) {
        if (entity != null && entity.getOrderNum() == null) {
            entity.setOrderNum(Constants.DEFAULT_ORDER_NUM);
        }
    }
}
