/*
 * Copyright 2018-2021 guerlab.net and other contributors.
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

package net.guerlab.cloud.core.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * 可自动加载转换器
 *
 * @param <S>
 *         源对象类型
 * @param <T>
 *         目标对象类型
 * @author guer
 */
public interface AutoLoadConverter<S, T> extends Converter<S, T> {}
