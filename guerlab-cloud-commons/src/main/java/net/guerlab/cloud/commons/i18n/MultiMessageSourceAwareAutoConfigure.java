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

package net.guerlab.cloud.commons.i18n;

import java.util.Collection;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

/**
 * 消息源处理.
 *
 * @author guer
 */
@Configurable
@AutoConfigureAfter(MessageSourceAutoConfiguration.class)
public class MultiMessageSourceAwareAutoConfigure {

	/**
	 * 注册消息源.
	 *
	 * @param messageSource 信息源
	 * @param providers     消息源处理提供者列表
	 */
	@Autowired
	public void registerMultiMessageSourceProvider(MessageSource messageSource, ObjectProvider<MultiMessageSourceProvider> providers) {
		if (!(messageSource instanceof AbstractResourceBasedMessageSource resourceBasedMessageSource)) {
			return;
		}

		providers.stream().map(MultiMessageSourceProvider::get).distinct()
				.forEach(resourceBasedMessageSource::addBasenames);
	}

	/**
	 * 注册消息源.
	 *
	 * @param messageSource  信息源
	 * @param multiProviders 消息源列表处理提供者列表
	 */
	@Autowired
	public void registerMultiMessageBatchSourceProvider(MessageSource messageSource, ObjectProvider<MultiMessageBatchSourceProvider> multiProviders) {
		if (!(messageSource instanceof AbstractResourceBasedMessageSource resourceBasedMessageSource)) {
			return;
		}

		multiProviders.stream().map(MultiMessageBatchSourceProvider::get).flatMap(Collection::stream)
				.distinct()
				.forEach(resourceBasedMessageSource::addBasenames);
	}
}
