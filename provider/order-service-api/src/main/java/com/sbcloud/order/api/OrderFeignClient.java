package com.sbcloud.order.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 庞飞
 * @date: 2020/4/28 12:43
 * @description TODO
 */
@FeignClient(value = "order-service", path = "/order", fallbackFactory = OrderFeignClientFallbackFactory.class)
public interface OrderFeignClient {

    @PostMapping("/create")
    String create(String userId);

    @GetMapping("/list")
    String list();

}
