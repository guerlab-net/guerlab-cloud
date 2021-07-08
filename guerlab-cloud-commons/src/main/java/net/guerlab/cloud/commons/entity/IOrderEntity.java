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
package net.guerlab.cloud.commons.entity;

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
     * @param o
     *         参与排序对象
     * @return 小于0时，在参与排序对象之前。
     * 大于0是，在参与排序对象之后。
     * 等于0时，顺序保持不变
     */
    @Override
    default int compareTo(E o) {
        return o.getOrderNum() - getOrderNum();
    }
}
