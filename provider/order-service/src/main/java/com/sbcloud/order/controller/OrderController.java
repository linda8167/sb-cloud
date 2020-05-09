package com.sbcloud.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.sbcloud.order.api.OrderFeignClient;
import com.sbcloud.user.api.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 庞飞
 * @date: 2020/5/9 14:22
 * @description TODO
 */
@RestController
@RequestMapping("order")
public class OrderController implements OrderFeignClient {

    @Autowired
    UserFeignClient userFeignClient;

    @GetMapping("/create/{userId}")
    @SentinelResource("resoure")
    public String create(@PathVariable("userId") String userId) {
        String userInfo = userFeignClient.get(userId);
        return userInfo + "创建订单";
    }

    @GetMapping("/list")
    public String list() {
        return "我的订单列表";
    }
}
