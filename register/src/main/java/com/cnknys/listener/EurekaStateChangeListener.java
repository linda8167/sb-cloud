package com.cnknys.listener;

import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author 庞飞
 * @date: 2020/4/27 14:11
 * @description TODO
 */
@Component
public class EurekaStateChangeListener {

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        System.out.println(event.getServerId() + "\t" + event.getAppName() + "服务下线");
    }


    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        System.out.println(event.getInstanceInfo().getAppName() + "服务注册");
    }


    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        System.out.println(event.getServerId() + "\t" + event.getAppName() + "服务续约");
    }


    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        System.out.println("注册中心启动");
    }


    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        System.out.println("Eureka Server启动");
    }

}
