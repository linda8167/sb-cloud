package com.sbcloud.order.api;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
final class OrderFeignClientFallbackFactory implements FallbackFactory<OrderFeignClient> {

    @Override
    public OrderFeignClient create(Throwable throwable) {
        log.error("OrderFeignClientFallbackFactory：");
        return new OrderFeignClient() {
            @Override
            public String list() {
                return "OrderFeignClientFallbackFactory";
            }

            @Override
            public String create(String userId) {
                return null;
            }
        };
    }
}
