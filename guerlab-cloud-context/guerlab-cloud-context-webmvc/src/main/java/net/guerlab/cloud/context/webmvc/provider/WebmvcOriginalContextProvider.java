/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

package net.guerlab.cloud.context.webmvc.provider;

import jakarta.annotation.Nullable;

import org.springframework.web.context.request.RequestContextHolder;

import net.guerlab.cloud.context.core.OriginalContextProvider;

/**
 * webmvc环境下原始上下文获取方式.
 *
 * @author guer
 */
public class WebmvcOriginalContextProvider implements OriginalContextProvider {

	@Nullable
	@Override
	public Object get() {
		return RequestContextHolder.getRequestAttributes();
	}
}
