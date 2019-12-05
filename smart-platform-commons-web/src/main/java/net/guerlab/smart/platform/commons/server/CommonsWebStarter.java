package net.guerlab.smart.platform.commons.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 公共服务启动
 *
 * @author guer
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CommonsWebStarter {

    public static void main(String[] args) {
        SpringApplication.run(CommonsWebStarter.class, args);
    }
}
