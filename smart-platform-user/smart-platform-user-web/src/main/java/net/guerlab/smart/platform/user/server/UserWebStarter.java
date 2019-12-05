package net.guerlab.smart.platform.user.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 用户服务启动
 *
 * @author guer
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("net.guerlab.smart.platform.**.mapper")
public class UserWebStarter {

    public static void main(String[] args) {
        SpringApplication.run(UserWebStarter.class, args);
    }

}
