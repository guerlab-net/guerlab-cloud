package net.guerlab.smart.platform.server.openapi.autoconfigure;

import net.guerlab.smart.platform.server.openapi.properties.OpenApiProperties;
import net.guerlab.spring.web.properties.ResponseAdvisorProperties;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * OpenApi相关路径配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties({ OpenApiProperties.class })
public class OpenApiAutoconfigure {

    @Autowired(required = false)
    public void responseAdvisorAddExcluded(ResponseAdvisorProperties responseAdvisorProperties,
            SpringDocConfigProperties properties) {
        if (responseAdvisorProperties != null && properties != null) {
            responseAdvisorProperties.addExcluded(Collections.singletonList(properties.getApiDocs().getPath()));
        }
    }

}
