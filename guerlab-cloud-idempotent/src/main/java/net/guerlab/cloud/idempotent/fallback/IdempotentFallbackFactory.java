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

package net.guerlab.cloud.idempotent.fallback;

/**
 * 幂等快速失败处理工厂
 *
 * @param <T>
 *         返回结果类型
 * @author guer
 */
public interface IdempotentFallbackFactory<T> {

    /**
     * 创建快速失败结果
     *
     * @param args
     *         参数列表
     * @return 快速失败结果
     */
    T create(Object[] args);
}
