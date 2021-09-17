package net.guerlab.cloud.commons.i18n;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

import java.util.List;

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
    public void handler(MessageSource messageSource, ObjectProvider<List<MultiMessageSourceProvider>> listProvider) {
        if (!(messageSource instanceof AbstractResourceBasedMessageSource)) {
            return;
        }
        AbstractResourceBasedMessageSource resourceBasedMessageSource = (AbstractResourceBasedMessageSource) messageSource;
        listProvider.ifUnique(list -> list.stream().map(MultiMessageSourceProvider::get)
                .forEach(resourceBasedMessageSource::addBasenames));
    }
}
