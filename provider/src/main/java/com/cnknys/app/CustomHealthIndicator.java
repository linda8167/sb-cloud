package com.cnknys.app;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * @author 庞飞
 * @date: 2020/4/27 14:05
 * @description TODO
 */
@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {


    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // builder.down().withDetail("status", false);
    }
}
