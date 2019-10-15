package com.gupaovip.service;

import com.gupaovip.service.annotation.Limited;
import com.gupaovip.service.annotation.Timeout;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

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

//    private ExecutorService executorService=Executors.newFixedThreadPool(2);

    @Timeout(value = 50L, fallback = "fallbackHello")
    @RequestMapping("/hello")
    public String hello() throws InterruptedException, ExecutionException, TimeoutException {

//      Future<String> future = executorService.submit(this::doHello);
//      return future.get(50, TimeUnit.MILLISECONDS);

        return doHello();
    }

    public String doHello() {
        await();
        return "Hello";
    }

    public String fallbackHello() {
        return "FALLBACK";
    }

    private Environment environment;

    public EchoServiceController(Environment environment) {
        this.environment = environment;
    }

    String getPort() {
        return environment.getProperty("local.server.port");
    }

    @Limited(1)
    @Timeout(value = 50L, fallback = "fallback")
   /* @HystrixCommand(
            fallbackMethod = "fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50")
            }
    )*/
    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message) {
        await();
        return "[Echo : " + getPort() + "] " + message;
    }

    public String fallback(String abc) {
        return "FALLBACK : " + abc;
    }

    private Random random = new Random();

    private void await() {
        long wait = random.nextInt(100);
        System.out.printf("[当前线程 : %s] 当前方法执行(模拟)消耗 %d 毫秒\n", Thread.currentThread().getName(), wait);
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Limited(10)  // 限流
    @RequestMapping("/world")
    public String world() {
        await();
        return "world";
    }

}
