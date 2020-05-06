package com.sbcloud.app;

import com.sbcloud.config.EnableUserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author 庞飞
 * @date: 2020/4/27 9:23
 * @description TODO
 */
@SpringBootApplication
@EnableUserClient
@EnableEurekaClient
// @EnableHystrix
public class ProviderApp {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApp.class, args);
    }
}
