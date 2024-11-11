/*
 * Copyright 2018-2025 guerlab.net and other contributors.
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
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import org.springframework.beans.factory.ObjectProvider;

/**
 * 元数据处理链.
 *
 * @author guer
 */
@Slf4j
public class MetaObjectHandlerChain implements MetaObjectHandler {

	@Resource
	private ObjectProvider<MetaObjectHandler> handlers;

	@Override
	public void insertFill(MetaObject metaObject) {
		handlers.stream().filter(this::canUse).filter(MetaObjectHandler::openInsertFill)
				.forEach(handler -> {
					log.debug("use MetaObjectHandler: {}", handler);
					handler.insertFill(metaObject);
				});
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		handlers.stream().filter(this::canUse).filter(MetaObjectHandler::openUpdateFill)
				.forEach(handler -> {
					log.debug("use MetaObjectHandler: {}", handler);
					handler.updateFill(metaObject);
				});
	}

	private boolean canUse(MetaObjectHandler handler) {
		return !(handler instanceof MetaObjectHandlerChain);
	}
}
