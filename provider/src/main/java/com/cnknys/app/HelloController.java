package com.cnknys.app;

import com.cnknys.config.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 庞飞
 * @date: 2020/4/27 9:34
 * @description TODO
 */
@RestController
public class HelloController {


    @Autowired
    UserClient userClient;

    //@GetMapping("timeout")
    //public String timeOut(@RequestParam(required = false, defaultValue = "2000", value = "t") Integer t) {
    //    try {
    //        Thread.sleep(t);
    //    } catch (InterruptedException e) {
    //        e.printStackTrace();
    //    }
    //    return LocalDateTime.now().toString();
    //}
    //
    //@GetMapping("hello")
    //public String sayConfig() {
    //    System.out.println("hello");
    //    return "Hello name:" + userClient.getName() + " age:" + userClient.getAge();
    //}

    @GetMapping("/house/data")
    public HouseInfo getData(@RequestParam("name") String name) {

        return new HouseInfo(System.nanoTime(), "北京", "海淀", "宝盛");

    }


    @GetMapping("/house/data/{name}")
    public String getData2(@PathVariable("name") String name) {
        return name;
    }

    @PostMapping("/house/save")
    public Long addData(@RequestBody HouseInfo houseInfo) {

        System.out.println(houseInfo.getOne());
        return System.nanoTime();
    }


}
