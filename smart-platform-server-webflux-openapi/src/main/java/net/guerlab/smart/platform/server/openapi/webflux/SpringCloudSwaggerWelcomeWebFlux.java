/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.smart.platform.server.openapi.webflux;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springdoc.webflux.ui.SwaggerWelcomeCommon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.condition.PatternsRequestCondition;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.pattern.PathPattern;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.Map.Entry;

import static net.guerlab.smart.platform.server.openapi.Constants.CLOUD_GATEWAY_PATH;
import static net.guerlab.smart.platform.server.openapi.Constants.CLOUD_PREFIX;
import static org.springdoc.core.Constants.SWAGGER_CONFIG_URL;
import static org.springdoc.core.Constants.SWAGGGER_CONFIG_FILE;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;

/**
 * swagger相关
 *
 * @author guer
 */
@Controller
@RequestMapping(CLOUD_PREFIX)
public class SpringCloudSwaggerWelcomeWebFlux extends SwaggerWelcomeCommon {

    private final RequestMappingInfoHandlerMapping requestMappingHandlerMapping;

    private String webfluxBasePath = "";

    @Value(CLOUD_GATEWAY_PATH)
    private String cloudGatewayPath;

    public SpringCloudSwaggerWelcomeWebFlux(SwaggerUiConfigProperties swaggerUiConfig,
            SpringDocConfigProperties springDocConfigProperties, SwaggerUiConfigParameters swaggerUiConfigParameters,
            Optional<WebFluxProperties> webFluxPropertiesOptional,
            RequestMappingInfoHandlerMapping requestMappingHandlerMapping) {
        super(swaggerUiConfig, springDocConfigProperties, swaggerUiConfigParameters);
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        webFluxPropertiesOptional
                .ifPresent((webFluxProperties) -> this.webfluxBasePath = webFluxProperties.getBasePath());
    }

    @PostConstruct
    private void init() {
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        List<Entry<RequestMappingInfo, HandlerMethod>> entries = new ArrayList<>(map.entrySet());
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : entries) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            PatternsRequestCondition patternsRequestCondition = requestMappingInfo.getPatternsCondition();
            Set<PathPattern> patterns = patternsRequestCondition.getPatterns();
            for (PathPattern pathPattern : patterns) {
                String operationPath = pathPattern.getPatternString();
                if (operationPath.endsWith("swagger-config")) {
                    this.swaggerConfigUrl = StringUtils.defaultString(this.webfluxBasePath) + operationPath;
                } else if (operationPath.endsWith(this.springDocConfigProperties.getApiDocs().getPath())) {
                    this.apiDocsUrl = StringUtils.defaultString(this.webfluxBasePath) + operationPath;
                }
            }
        }
    }

    @Operation(hidden = true)
    @GetMapping(value = SWAGGER_CONFIG_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Override
    public Map<String, Object> getSwaggerUiConfig(ServerHttpRequest request) {
        return super.getSwaggerUiConfig(request);
    }

    @Override
    protected void calculateOauth2RedirectUrl(UriComponentsBuilder uriComponentsBuilder) {
        if (this.oauthPrefix == null && !this.swaggerUiConfigParameters
                .isValidUrl(this.swaggerUiConfigParameters.getOauth2RedirectUrl())) {
            this.oauthPrefix = uriComponentsBuilder.path(this.webfluxBasePath)
                    .path(this.swaggerUiConfigParameters.getUiRootPath()).path(this.webJarsPrefixUrl);
            this.swaggerUiConfigParameters.setOauth2RedirectUrl(
                    this.oauthPrefix.path(this.swaggerUiConfigParameters.getOauth2RedirectUrl()).build().toString());
        }
    }

    @Override
    protected void calculateUiRootPath(StringBuilder... sbUrls) {
        StringBuilder sbUrl = new StringBuilder();
        calculateUiRootCommon(sbUrl, sbUrls);
    }

    @Override
    protected String buildApiDocUrl() {
        return buildUrl(getGatewayPath() + contextPath, springDocConfigProperties.getApiDocs().getPath());
    }

    @Override
    protected String buildSwaggerConfigUrl() {
        return apiDocsUrl + DEFAULT_PATH_SEPARATOR + SWAGGGER_CONFIG_FILE;
    }

    private String getGatewayPath() {
        String gatewayPath = StringUtils.trimToNull(cloudGatewayPath);

        if (gatewayPath == null) {
            return "";
        }

        if (gatewayPath.startsWith("/")) {
            return gatewayPath;
        } else {
            return "/" + gatewayPath;
        }
    }
}
