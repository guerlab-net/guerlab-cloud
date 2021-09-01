package net.guerlab.cloud.commons.i18n;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;

/**
 * 公共多消息源处理提供者自动配置
 *
 * @author guer
 */
@Configurable
@AutoConfigureBefore(MultiMessageSourceAwareAutoConfigure.class)
public class CommonsMultiMessageSourceProviderAutoConfigure {

    /**
     * 公共多消息源处理提供者
     *
     * @return 公共多消息源处理提供者
     */
    @Bean
    public MultiMessageSourceProvider commonsMultiMessageSourceProvider() {
        return () -> "messages/commons";
    }
}
