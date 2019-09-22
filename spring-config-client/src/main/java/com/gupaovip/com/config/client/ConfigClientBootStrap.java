package com.gupaovip.com.config.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * ClassName:ConfigClientBootStrap
 * Package:com.gupaovip.com.config.client
 * description
 * Created by zhangbin on 2019/9/20.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/20 10:51
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
public class ConfigClientBootStrap {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientBootStrap.class,args);
    }

    @Autowired
    private DiscoveryClient discoveryClient;
    @GetMapping("/services")
    public Set<String> getServices(){
         return new LinkedHashSet<>(discoveryClient.getServices());
    }

    @GetMapping("/services/{serviceName}")
    public List<ServiceInstance> getServiceInstaces(@PathVariable String serviceName){
        return discoveryClient.getInstances(serviceName);
    }

    @GetMapping("/services/{serviceName}/{instanceId}")
    public ServiceInstance getServiceInstaces(@PathVariable String serviceName,@PathVariable String instanceId){
        return getServiceInstaces(serviceName)
                .stream()
                .filter(serviceInstance -> instanceId.equals(serviceInstance.getInstanceId()))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("No Such service instance"))
                ;
    }
}
