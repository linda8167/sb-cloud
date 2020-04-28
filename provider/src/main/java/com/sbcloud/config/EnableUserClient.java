package com.sbcloud.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 庞飞
 * @date: 2020/4/27 10:06
 * @description TODO
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(KnysAutoConfigure.class)
public @interface EnableUserClient {
}
