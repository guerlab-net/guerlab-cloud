package net.guerlab.cloud.auth.webflux.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author guer
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TestWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestWebfluxApplication.class, args);
    }
}
