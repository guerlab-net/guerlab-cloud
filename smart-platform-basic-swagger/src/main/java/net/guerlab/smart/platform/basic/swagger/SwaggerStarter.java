package net.guerlab.smart.platform.basic.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * swagger聚合启动程序
 *
 * @author guer
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SwaggerStarter {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerStarter.class, args);
    }
}
