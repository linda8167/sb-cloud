package com.sbcloud.api;

import com.sbcloud.api.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 庞飞
 * @date: 2020/4/28 12:43
 * @description TODO
 */
@FeignClient(value = "provider")
public interface UserRemoteClient {

    @PostMapping("/user/add")
    String addUser(@RequestBody User user);

    @GetMapping("hello")
    String hello();

    @GetMapping("timeout")
    String timeout(@RequestParam("t") Integer t);
}
