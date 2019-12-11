package net.guerlab.smart.platform.user.web.autoconfigure;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户服务自动注册
 *
 * @author guer
 */
@Configurable
@ComponentScan("net.guerlab.smart.platform.user.web")
public class UserWebAutoconfigure {}
