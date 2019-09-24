package com.gupaovip.service.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName:EchoServiceController
 * Package:com.gupaovip.service.client
 * description
 * Created by zhangbin on 2019/9/22.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/22 18:36
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class EchoServiceBootStrap {

    private final EchoServiceClient echoServiceClient;
    @LoadBalanced
    private final RestTemplate restTemplate;

    public EchoServiceBootStrap(EchoServiceClient echoServiceClient, RestTemplate restTemplate) {
        this.echoServiceClient = echoServiceClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/call/echo/{message}")
    public String callEcho(@PathVariable String message) {
        return echoServiceClient.echo(message);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EchoServiceBootStrap.class, args);
    }
}
