package com.sbcloud.app;

import com.sbcloud.api.UserRemoteClient;
import com.sbcloud.domain.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author 庞飞
 * @date: 2020/4/28 13:26
 * @description TODO
 */
@RestController
public class UserController implements UserRemoteClient {

    @Override
    public String addUser(@RequestBody User user) {
        System.out.println("新增用户" + user.getName());
        return user.getName();
    }

    @Override
    public String hello() {
        System.out.println("hello");
        return "Hello name";
    }

    @Override
    public String timeout(Integer t) {
        try {
            System.out.println("休眠" + t + "ms");
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return LocalDateTime.now().toString();
    }
}
