package net.guerlab.smart.platform.user.auth.properties;

import net.guerlab.smart.platform.basic.auth.properties.AuthProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 认证配置
 *
 * @author guer
 */
@RefreshScope
@ConfigurationProperties(prefix = "auth.user")
public class UserAuthProperties extends AuthProperties {}
