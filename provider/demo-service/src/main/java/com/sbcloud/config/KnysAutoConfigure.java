package com.sbcloud.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 庞飞
 * @date: 2020/4/27 9:50
 * @description TODO
 */
@Configuration
@EnableConfigurationProperties(KnysProperties.class)
public class KnysAutoConfigure {

    @Bean
    @ConditionalOnProperty(prefix = "knys.user", value = "enabled", havingValue = "true")
    public UserClient getKnysConfig(KnysProperties knysProperties) {
        return new UserClient(knysProperties);
    }
}
