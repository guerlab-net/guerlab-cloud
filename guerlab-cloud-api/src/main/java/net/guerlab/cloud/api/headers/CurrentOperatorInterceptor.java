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

package net.guerlab.cloud.api.headers;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import net.guerlab.cloud.auth.context.AbstractContextHandler;
import net.guerlab.cloud.commons.Constants;

/**
 * 当前操作者信息处理请求拦截器.
 *
 * @author guer
 */
public class CurrentOperatorInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		String currentOperator = AbstractContextHandler.getCurrentOperator();
		if (currentOperator != null) {
			template.header(Constants.CURRENT_OPERATOR_HEADER, currentOperator);
		}
	}
}
