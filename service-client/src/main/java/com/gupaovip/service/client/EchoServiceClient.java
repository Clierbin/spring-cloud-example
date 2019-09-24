package com.gupaovip.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName:EchoServiceClient
 * Package:com.gupaovip.service.client
 * description
 * Created by zhangbin on 2019/9/22.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/22 18:38
 */
@FeignClient("service-provider")
public interface EchoServiceClient {
    @GetMapping("/echo/{message}")
    String echo(@PathVariable String message);
}
