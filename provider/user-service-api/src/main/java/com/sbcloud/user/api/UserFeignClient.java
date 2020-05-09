package com.sbcloud.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 庞飞
 * @date: 2020/4/28 12:43
 * @description TODO
 */
@FeignClient(value = "user-service", path = "user", fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient {

    @GetMapping("/get/{id}")
    String get(@PathVariable("id") String id);

    @GetMapping("/list")
    String list();

}
