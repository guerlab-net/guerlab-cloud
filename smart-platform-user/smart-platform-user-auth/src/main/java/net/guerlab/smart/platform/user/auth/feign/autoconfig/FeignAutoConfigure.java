package net.guerlab.smart.platform.user.auth.feign.autoconfig;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * feign自动配置
 *
 * @author guer
 */
@Configuration
@EnableFeignClients("net.guerlab.smart.platform.user.auth.feign")
public class FeignAutoConfigure {

}
