package net.guerlab.smart.platform.user.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务启动
 *
 * @author guer
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserWebStarter {

    public static void main(String[] args) {
        SpringApplication.run(UserWebStarter.class, args);
    }

}
