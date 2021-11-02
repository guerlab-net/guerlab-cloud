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
package net.guerlab.cloud.server.service;

import net.guerlab.spring.searchparams.AbstractSearchParams;

import java.io.Serializable;

/**
 * 基本服务接口
 *
 * @param <T>
 *         数据类型
 * @param <PK>
 *         主键类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
public interface BaseService<T, PK extends Serializable, SP extends AbstractSearchParams>
        extends BaseFindService<T, PK, SP>, BaseSaveService<T, SP>, BaseUpdateService<T, SP>, BaseDeleteService<T, PK, SP>, QueryWrapperGetter<T, SP> {

}
