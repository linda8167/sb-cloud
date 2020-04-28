package com.sbcloud.api.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author 庞飞
 * @date: 2020/4/28 13:50
 * @description TODO
 */
@Data
@Builder
public class User {

    private String name;

    private String sex;

    private Integer age;
}
