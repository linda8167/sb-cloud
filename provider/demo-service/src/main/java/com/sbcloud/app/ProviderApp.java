package com.sbcloud.app;

import com.sbcloud.config.EnableUserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 庞飞
 * @date: 2020/4/27 9:23
 * @description TODO
 */
@SpringBootApplication
@EnableUserClient
@EnableDiscoveryClient
// @EnableEurekaClient
// @EnableHystrix
public class ProviderApp {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApp.class, args);
    }
}
