package com.gupaovip.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * ClassName:ServiceProviderBootStrap
 * Package:com.gupaovip.service
 * description
 * Created by zhangbin on 2019/9/22.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/22 18:15
 */
@EnableAutoConfiguration
@ComponentScan
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableAspectJAutoProxy
//@EnableBinding(Sink.class)
public class ServiceProviderBootStrap {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderBootStrap.class,args);
    }

//    @StreamListener(Sink.INPUT)
//    public void listen(byte[] data){
//        System.out.println(new String(data));
//    }
}
