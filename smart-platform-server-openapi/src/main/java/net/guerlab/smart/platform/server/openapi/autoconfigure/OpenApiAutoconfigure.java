package net.guerlab.smart.platform.server.openapi.autoconfigure;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import net.guerlab.smart.platform.commons.Constants;
import net.guerlab.smart.platform.server.openapi.properties.ApiInfoProperties;
import net.guerlab.spring.commons.properties.ResponseAdvisorProperties;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * OpenApi相关路径配置
 *
 * @author guer
 */
@Configuration
@EnableConfigurationProperties({ ApiInfoProperties.class })
public class OpenApiAutoconfigure {

    @Autowired(required = false)
    public void responseAdvisorAddExcluded(ResponseAdvisorProperties responseAdvisorProperties,
            SpringDocConfigProperties properties) {
        if (responseAdvisorProperties != null && properties != null) {
            responseAdvisorProperties.addExcluded(Collections.singletonList(properties.getApiDocs().getPath()));
        }
    }

    @ConditionalOnClass(ApiInfoProperties.class)
    @Bean
    public Info customerInfo(ApiInfoProperties properties) {
        License license = new License();
        license.setName(properties.getLicense());
        license.setUrl(properties.getLicenseUrl());

        Info info = new Info();
        info.title(properties.getTitle());
        info.version(properties.getVersion());
        info.description(properties.getDescription());
        info.termsOfService(properties.getTermsOfServiceUrl());
        info.license(license);

        return info;
    }

    @Bean
    public Components customerComponents() {
        SecurityScheme authorization = new SecurityScheme();
        authorization.type(SecurityScheme.Type.APIKEY);
        authorization.name(Constants.TOKEN);
        authorization.in(SecurityScheme.In.HEADER);

        Components components = new Components();
        components.addSecuritySchemes(Constants.TOKEN, authorization);

        return components;
    }

    @Bean
    @ConditionalOnClass({ Info.class, Components.class })
    public OpenAPI customOpenApi(Info info, Components components) {
        OpenAPI openApi = new OpenAPI();
        openApi.info(info);
        openApi.components(components);
        return openApi;
    }

}
