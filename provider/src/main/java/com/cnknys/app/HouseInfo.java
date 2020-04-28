package com.cnknys.app;

import lombok.Data;

/**
 * @author 庞飞
 * @date: 2020/4/27 14:44
 * @description TODO
 */
@Data
public class HouseInfo {

    private long id;

    private String one;

    private String two;

    private String three;

    public HouseInfo(long id, String one, String two, String three) {
        this.id = id;
        this.one = one;
        this.two = two;
        this.three = three;
    }
}
