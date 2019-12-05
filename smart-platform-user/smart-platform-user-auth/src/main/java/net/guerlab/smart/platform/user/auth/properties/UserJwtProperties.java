package net.guerlab.smart.platform.user.auth.properties;

import net.guerlab.smart.platform.basic.auth.properties.JwtProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * jwt配置
 *
 * @author guer
 */
@RefreshScope
@ConfigurationProperties(prefix = "jwt.user")
public class UserJwtProperties extends JwtProperties {}
