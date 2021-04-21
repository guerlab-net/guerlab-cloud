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
package net.guerlab.smart.platform.server.openapi.webmvc;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static net.guerlab.smart.platform.server.openapi.Constants.CLOUD_GATEWAY_PATH;
import static net.guerlab.smart.platform.server.openapi.Constants.CLOUD_PREFIX;
import static org.springdoc.core.Constants.*;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;

/**
 * swagger相关
 *
 * @author guer
 */
@Slf4j
@Controller
@RequestMapping(CLOUD_PREFIX)
public class SpringCloudSwaggerWelcomeWebMvc extends SwaggerWelcomeCommon {

    @Value(MVC_SERVLET_PATH)
    private String mvcServletPath;

    @Value(CLOUD_GATEWAY_PATH)
    private String cloudGatewayPath;

    public SpringCloudSwaggerWelcomeWebMvc(SwaggerUiConfigProperties swaggerUiConfig,
            SpringDocConfigProperties springDocConfigProperties, SwaggerUiConfigParameters swaggerUiConfigParameters) {
        super(swaggerUiConfig, springDocConfigProperties, swaggerUiConfigParameters);
    }

    @Operation(hidden = true)
    @GetMapping(value = SWAGGER_CONFIG_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Override
    public Map<String, Object> openapiJson(HttpServletRequest request) {
        return super.openapiJson(request);
    }

    @Override
    protected void calculateUiRootPath(StringBuilder... sbUrls) {
        StringBuilder sbUrl = new StringBuilder();
        if (StringUtils.isNotBlank(mvcServletPath)) {
            sbUrl.append(mvcServletPath);
        }
        calculateUiRootCommon(sbUrl, sbUrls);
    }

    @Override
    protected String buildApiDocUrl(String contextPath) {
        return buildUrl(getGatewayPath() + contextPath, springDocConfigProperties.getApiDocs().getPath());
    }

    @Override
    protected String buildSwaggerConfigUrl(String contextPath) {
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
