package com.gupaovip.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

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
public class ServiceProviderBootStrap {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderBootStrap.class,args);
    }
}
