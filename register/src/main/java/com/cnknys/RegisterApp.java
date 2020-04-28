package com.cnknys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 庞飞
 * @date: 2020/4/27 10:45
 * @description TODO
 */
@SpringBootApplication
@EnableEurekaServer
public class RegisterApp {

    public static void main(String[] args) {
        SpringApplication.run(RegisterApp.class, args);
    }
}
