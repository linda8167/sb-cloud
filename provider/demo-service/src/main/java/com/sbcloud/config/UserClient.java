package com.sbcloud.config;

/**
 * @author 庞飞
 * @date: 2020/4/27 9:48
 * @description TODO
 */
public class UserClient {
    private KnysProperties properties;

    public UserClient(KnysProperties properties) {
        this.properties = properties;
    }

    public String getName() {
        return properties.getName();
    }

    public int getAge() {
        return properties.getAge();
    }
}
