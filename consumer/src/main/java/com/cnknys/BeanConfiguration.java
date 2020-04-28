package com.cnknys;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 庞飞
 * @date: 2020/4/27 11:15
 * @description TODO
 */
@Configuration
public class BeanConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    //@Bean
    //public IRule myRule() {
    //    return new MyRule();
    //}


    @Bean("house1")
    @Qualifier("good")
    public HouseInfo houseInfo1() {
        return HouseInfo.builder().one("good").build();
    }


    @Bean("house2")
    @Qualifier("bad")
    public HouseInfo houseInfo2() {
        return HouseInfo.builder().one("bad").build();
    }

    @Bean("house3")
    @Qualifier("bad")
    public HouseInfo houseInfo3() {
        return HouseInfo.builder().one("bad").build();
    }

    @Bean("house4")
    @Qualifier("bad")
    public HouseInfo houseInfo4() {
        return HouseInfo.builder().one("bad").build();
    }

    @Bean("house5")
    @Qualifier("good")
    public HouseInfo houseInfo5() {
        return HouseInfo.builder().one("good").build();
    }

    @Bean("house6")
    @Qualifier("")
    public HouseInfo houseInfo() {
        return HouseInfo.builder().one("what").build();
    }


}
