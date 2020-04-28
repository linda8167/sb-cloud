package com.cnknys.app;

import com.cnknys.config.EnableUserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 庞飞
 * @date: 2020/4/27 9:23
 * @description TODO
 */
@SpringBootApplication
@EnableUserClient
@EnableEurekaClient
public class ProviderApp {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApp.class, args);
    }
}
