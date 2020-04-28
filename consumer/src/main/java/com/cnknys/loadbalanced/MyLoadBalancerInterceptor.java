package com.cnknys.loadbalanced;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;

/**
 * @author 庞飞
 * @date: 2020/4/27 15:14
 * @description TODO
 */
public class MyLoadBalancerInterceptor implements ClientHttpRequestInterceptor {


    private LoadBalancerClient loadBalancerClient;

    private LoadBalancerRequestFactory requestFactory;

    public MyLoadBalancerInterceptor(LoadBalancerClient loadBalancerClient, LoadBalancerRequestFactory requestFactory) {
        this.loadBalancerClient = loadBalancerClient;
        this.requestFactory = requestFactory;
    }

    public MyLoadBalancerInterceptor(LoadBalancerClient loadBalancerClient) {
        this(loadBalancerClient, new LoadBalancerRequestFactory(loadBalancerClient));
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution executorService) throws IOException {
        final URI originalUri = httpRequest.getURI();
        String serviceName = originalUri.getHost();
        System.out.println("进入自定义请求拦截器" + serviceName);
        Assert.state(serviceName != null, "" + originalUri);

        // return clientHttpRequestExecution.execute(httpRequest, bytes);

        return loadBalancerClient.execute(serviceName, requestFactory.createRequest(httpRequest, body, executorService));
    }
}
