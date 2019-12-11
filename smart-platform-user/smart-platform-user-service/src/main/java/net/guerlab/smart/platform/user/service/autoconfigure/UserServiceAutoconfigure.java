package net.guerlab.smart.platform.user.service.autoconfigure;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 用户服务自动注册
 *
 * @author guer
 */
@Configurable
@ComponentScan("net.guerlab.smart.platform.user.service")
@MapperScan("net.guerlab.smart.platform.user.service.mapper")
public class UserServiceAutoconfigure {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
