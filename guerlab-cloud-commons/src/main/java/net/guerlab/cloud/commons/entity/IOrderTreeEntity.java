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

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 可排序树形结构
 *
 * @param <E>
 *         对象类型
 * @author guer
 */
@Schema(name = "IOrderTreeEntity", description = "可排序树形结构")
public interface IOrderTreeEntity<E extends IOrderTreeEntity<E>> extends ITreeEntity<E>, IOrderEntity<E> {}
