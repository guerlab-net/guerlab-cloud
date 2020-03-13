package net.guerlab.smart.platform.basic.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Admin启动程序
 *
 * @author guer
 */
@EnableAdminServer
@SpringBootApplication
@EnableDiscoveryClient
public class AdminStarter {

    public static void main(String[] args) {
        SpringApplication.run(AdminStarter.class, args);
    }
}
