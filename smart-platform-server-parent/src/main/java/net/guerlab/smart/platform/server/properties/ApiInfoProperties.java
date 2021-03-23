package net.guerlab.smart.platform.server.properties;

import io.swagger.v3.oas.models.info.Contact;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guer
 */
@Data
@ConfigurationProperties(prefix = "openapi.api-info")
public class ApiInfoProperties {

    private static final String DEFAULT_VERSION = "3.0.1";

    private String title;

    private String description;

    private String termsOfServiceUrl;

    private Contact contact;

    private String license;

    private String licenseUrl;

    private String version = DEFAULT_VERSION;
}
