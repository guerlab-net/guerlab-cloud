package net.guerlab.smart.platform.commons.util;

import net.guerlab.smart.platform.commons.Constants;
import net.guerlab.smart.platform.commons.entity.IOrderEntity;

/**
 * 可排序对象工具类
 *
 * @author guer
 */
public class OrderEntityUtils {

    private OrderEntityUtils() {

    }

    /**
     * 属性检查，当排序值为null时设置为默认值
     *
     * @param entity
     *         可排序对象
     */
    public static void propertiesCheck(IOrderEntity<?> entity) {
        if (entity != null && entity.getOrderNum() == null) {
            entity.setOrderNum(Constants.DEFAULT_ORDER_NUM);
        }
    }
}
