package net.guerlab.smart.platform.basic.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关启动程序
 *
 * @author guer
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayStarter {

    public static void main(String[] args) {
        SpringApplication.run(GatewayStarter.class, args);
    }
}
