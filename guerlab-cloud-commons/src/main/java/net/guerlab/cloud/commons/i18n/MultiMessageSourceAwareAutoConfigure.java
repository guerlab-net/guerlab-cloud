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

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

/**
 * 多消息源处理
 *
 * @author guer
 */
@Configurable
@AutoConfigureAfter(MessageSourceAutoConfiguration.class)
public class MultiMessageSourceAwareAutoConfigure {

    /**
     * 多消息源处理
     *
     * @param messageSource
     *         信息源
     * @param listProvider
     *         多消息源处理提供者列表
     */
    @Autowired
    public void handler(MessageSource messageSource, ObjectProvider<MultiMessageSourceProvider> listProvider) {
        if (!(messageSource instanceof AbstractResourceBasedMessageSource)) {
            return;
        }
        AbstractResourceBasedMessageSource resourceBasedMessageSource = (AbstractResourceBasedMessageSource) messageSource;
        listProvider.stream().map(MultiMessageSourceProvider::get).forEach(resourceBasedMessageSource::addBasenames);
    }
}
