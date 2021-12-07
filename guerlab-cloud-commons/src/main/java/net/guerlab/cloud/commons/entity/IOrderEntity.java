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

/**
 * 排序对象接口
 *
 * @param <E>
 *         对象类型
 * @author guer
 */
@Deprecated(since = "2020.1.0")
public interface IOrderEntity<E extends IOrderEntity<?>> extends IOrderlyEntity<E> {}
