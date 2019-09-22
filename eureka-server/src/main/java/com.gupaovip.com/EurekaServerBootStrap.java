package com.gupaovip.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * ClassName:EurekaServerBootStrap
 * Package:com.gupaovip.com
 * description
 * Created by zhangbin on 2019/9/19.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/19 21:12
 */

public class EurekaServerBootStrap {
    @EnableAutoConfiguration
    @EnableEurekaServer
    public static class EurekaServerConfiguration{

    }
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerConfiguration.class,args);
    }
}
