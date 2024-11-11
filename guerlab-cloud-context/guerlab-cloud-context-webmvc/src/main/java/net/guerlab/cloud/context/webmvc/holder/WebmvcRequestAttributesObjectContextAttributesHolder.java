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

package net.guerlab.cloud.context.webmvc.holder;

import org.springframework.web.context.request.RequestAttributes;

import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ObjectContextAttributesHolder;

/**
 * webmvc环境下基于ContextAttributes的上下文属性持有器.
 *
 * @author guer
 */
public class WebmvcRequestAttributesObjectContextAttributesHolder implements ObjectContextAttributesHolder {

	@Override
	public boolean accept(Object object) {
		return object instanceof RequestAttributes;
	}

	@Override
	public ContextAttributes get(Object object) {
		RequestAttributes request = (RequestAttributes) object;
		ContextAttributes contextAttributes = (ContextAttributes) request.getAttribute(ContextAttributes.KEY, RequestAttributes.SCOPE_REQUEST);

		if (contextAttributes == null) {
			contextAttributes = new ContextAttributes("RequestAttributes-" + request);
			request.setAttribute(ContextAttributes.KEY, contextAttributes, RequestAttributes.SCOPE_REQUEST);
		}

		return contextAttributes;
	}

	@Override
	public void set(Object object, ContextAttributes contextAttributes) {
		RequestAttributes request = (RequestAttributes) object;
		request.setAttribute(ContextAttributes.KEY, contextAttributes, RequestAttributes.SCOPE_REQUEST);
	}
}
