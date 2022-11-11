/*
 * Copyright 2018-2023 guerlab.net and other contributors.
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

package net.guerlab.cloud.context.webflux.holder;

import org.springframework.web.server.ServerWebExchange;

import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ObjectContextAttributesHolder;

/**
 * webflux环境下基于对象的上下文属性持有器.
 *
 * @author guer
 */
public class WebfluxObjectContextAttributesHolder implements ObjectContextAttributesHolder {

	@Override
	public boolean accept(Object object) {
		return object instanceof ServerWebExchange;
	}

	@Override
	public ContextAttributes get(Object object) {
		ServerWebExchange exchange = (ServerWebExchange) object;
		ContextAttributes contextAttributes = (ContextAttributes) exchange.getAttributes().get(ContextAttributes.KEY);

		if (contextAttributes == null) {
			contextAttributes = new ContextAttributes();
			exchange.getAttributes().put(ContextAttributes.KEY, contextAttributes);
		}

		return contextAttributes;
	}

	@Override
	public void set(Object object, ContextAttributes contextAttributes) {
		ServerWebExchange exchange = (ServerWebExchange) object;
		exchange.getAttributes().put(ContextAttributes.KEY, contextAttributes);
	}
}
