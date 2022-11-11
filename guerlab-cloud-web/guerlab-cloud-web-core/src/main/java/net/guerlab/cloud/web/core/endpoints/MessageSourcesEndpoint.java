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

package net.guerlab.cloud.web.core.endpoints;

import java.util.Collection;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

/**
 * 消息源监控端点.
 *
 * @author guer
 */
@Slf4j
@Endpoint(id = "message-sources")
public class MessageSourcesEndpoint {

	private final MessageSource messageSource;

	/**
	 * 创建实例.
	 *
	 * @param messageSource 消息源
	 */
	public MessageSourcesEndpoint(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * 获取消息源列表.
	 *
	 * @return 消息源列表
	 */
	@SuppressWarnings("unused")
	@ReadOperation
	public Collection<String> getFeignInstances() {
		if (!(messageSource instanceof AbstractResourceBasedMessageSource resourceBasedMessageSource)) {
			return Collections.singleton("warning: current messageSource is not AbstractResourceBasedMessageSource instance");
		}

		return resourceBasedMessageSource.getBasenameSet();
	}
}
