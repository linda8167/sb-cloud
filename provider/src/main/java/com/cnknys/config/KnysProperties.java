package com.cnknys.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 庞飞
 * @date: 2020/4/27 9:47
 * @description TODO
 */
@Data
@ConfigurationProperties("knys.user")
public class KnysProperties {
    private String name;

    private int age;
}
