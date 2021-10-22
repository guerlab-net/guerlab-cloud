package net.guerlab.cloud.auth.webmvc.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author guer
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TestWebmvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestWebmvcApplication.class, args);
    }
}
