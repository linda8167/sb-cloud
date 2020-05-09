package com.sbcloud.user.api;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
final class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable throwable) {
        log.error("UserFeignClientFallbackFactory：");
        return new UserFeignClient() {
            @Override
            public String list() {
                return "UserFeignClientFallbackFactory 失败啦";
            }

            @Override
            public String get(String id) {
                return null;
            }
        };
    }
}
