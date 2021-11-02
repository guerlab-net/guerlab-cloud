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
package net.guerlab.cloud.commons.entity;

import org.springframework.lang.Nullable;

/**
 * 排序对象接口
 *
 * @param <E>
 *         对象类型
 * @author guer
 */
public interface IOrderEntity<E extends IOrderEntity<?>> extends Comparable<E> {

    /**
     * 获取排序值
     *
     * @return 排序值
     */
    @Nullable
    Integer getOrderNum();

    /**
     * 设置排序值
     *
     * @param orderNum
     *         排序值
     */
    void setOrderNum(Integer orderNum);

    /**
     * 根据排序值返回排序顺序
     *
     * @param o1
     *         参与排序对象1
     * @param o2
     *         参与排序对象2
     * @return 小于0时，在参与排序对象之前。
     * 大于0时，在参与排序对象之后。
     * 等于0时，顺序保持不变
     */
    static int compareTo(IOrderEntity<?> o1, IOrderEntity<?> o2) {
        Integer self = o1.getOrderNum();
        Integer other = o2.getOrderNum();

        if (self == null && other == null) {
            return 0;
        } else if (self == null) {
            return 1;
        } else if (other == null) {
            return -1;
        } else {
            return other - self;
        }
    }

    /**
     * 根据排序值返回排序顺序
     *
     * @param o
     *         参与排序对象
     * @return 小于0时，在参与排序对象之前。
     * 大于0时，在参与排序对象之后。
     * 等于0时，顺序保持不变
     */
    @Override
    default int compareTo(E o) {
        return compareTo(this, o);
    }
}
