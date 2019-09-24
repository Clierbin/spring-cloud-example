package com.gupaovip.service;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:EchoServiceController
 * Package:com.gupaovip.service
 * description
 * Created by zhangbin on 2019/9/22.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/22 18:12
 */
@RestController
public class EchoServiceController {


    private Environment environment;

    public EchoServiceController(Environment environment) {
        this.environment = environment;
    }

    String getPort(){
        return environment.getProperty("local.server.port");
    }

    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message) {
        return "[Echo : "+getPort()+"] " + message;
    }
}
