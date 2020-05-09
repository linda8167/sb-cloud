package com.sbcloud.user.controller;

import com.sbcloud.user.api.UserFeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 庞飞
 * @date: 2020/5/9 14:22
 * @description TODO
 */
@RestController
@RequestMapping("/user")
public class UserController implements UserFeignClient {

    @RequestMapping("/get/{id}")
    public String get(@PathVariable("id") String id) {

        return "用户:" + id;
    }

    @RequestMapping("/list")
    public String list() {
        return "用户列表";
    }
}
