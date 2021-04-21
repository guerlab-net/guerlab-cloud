package net.guerlab.smart.platform.server.openapi.properties;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 开放接口配置
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties extends OpenAPI {

    /**
     * cloud环境下相关配置
     */
    private CloudProperties cloud;
}
