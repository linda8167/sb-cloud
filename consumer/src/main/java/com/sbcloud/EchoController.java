package com.sbcloud;

import com.sbcloud.api.UserRemoteClient;
import com.sbcloud.domain.User;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 庞飞
 */
@RestController
public class EchoController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    LoadBalancerClient loadBalancer;

    @Autowired
    private List<HouseInfo> houseAll = Collections.emptyList();

    @Autowired
    @Qualifier("good")
    private List<HouseInfo> goodHouse = Collections.emptyList();

    @Autowired
    @Qualifier("bad")
    private List<HouseInfo> badHouse = Collections.emptyList();

    @Autowired
    UserRemoteClient userRemoteClient;


    @GetMapping("feignHello")
    public String feignHello() {
        return userRemoteClient.hello();
    }


    @GetMapping("feignTimeout")
    public String feignTimeout(@RequestParam(required = false, defaultValue = "2000", value = "t") Integer t) {
        return userRemoteClient.timeout(t);
    }

    @GetMapping("feignAddUser")
    public String feignAddUser() {
        User user = User.builder().name("张三").age(28).sex("男").build();
        return userRemoteClient.addUser(user);
    }


    @GetMapping("timeout")
    public String timeOut(@RequestParam(required = false, defaultValue = "2000", value = "t") Integer t) {
        return restTemplate.getForObject("http://provider/timeout?t={t}", String.class, t);
    }

    @GetMapping("/service/info")
    public Object serviceInfo() {
        return loadBalancer.choose("provider");
    }

    @GetMapping("/house/all")
    public List<HouseInfo> allHouse() {
        List<HouseInfo> list = new ArrayList<>();
        list.addAll(goodHouse);
        list.addAll(badHouse);
        list.addAll(houseAll);
        return list;
    }

    @GetMapping("/house/data")
    public HouseInfo getData(@RequestParam("name") String name) {

        return restTemplate.getForObject("http://provider/house/data?name=" + name, HouseInfo.class);

    }


    @GetMapping("/house/data/{name}")
    public String getData2(@PathVariable("name") String name) {
        return restTemplate.getForObject("http://provider/house/data/{name}", String.class, name);

    }


    @GetMapping("/house/dataEntity")
    public HouseInfo getDataEntity(@RequestParam("name") String name) {
        ResponseEntity<HouseInfo> responseEntity = restTemplate.getForEntity("http://provider/house/data?name=" + name, HouseInfo.class);
        if (responseEntity.getStatusCodeValue() == 200) {
            return responseEntity.getBody();
        }
        return null;
    }

    @GetMapping("/house/save")
    public Long add() {
        HouseInfo houseInfo = HouseInfo.builder().id(1).one("上海").two("红犼").three("1234").build();

        Long id = restTemplate.postForObject("http://provider/house/save", houseInfo, Long.class);
        return id;
    }

    @GetMapping("hello")
    public String sayHello() {
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    @GetMapping("hello2")
    public String sayHello2() {
        return restTemplate.getForObject("http://provider/hello", String.class);
    }


    @GetMapping("services")
    public Object getService() {
        Object object = eurekaClient.getInstancesByVipAddress("provider", false);
        return object;
    }


    @GetMapping("services2")
    public Object getService2() {
        Object object = discoveryClient.getInstances("provider");
        return object;
    }

}
