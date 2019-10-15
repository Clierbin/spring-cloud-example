package com.gupaovip.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.Map;

/**
 * ClassName:DemoBootStrap
 * Package:com.gupaovip.service.client
 * description
 * Created by zhangbin on 2019/9/24.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/24 16:39
 */
@EnableAutoConfiguration
public class DemoBootStrap {

    @Autowired
    private Map<String,String> allStringBeans = Collections.emptyMap();

    @Autowired
//    @Qualifier
    @Group
    private Map<String,String> groupStringBeans = Collections.emptyMap();

    @Autowired
    @Qualifier("a")
    private String aBean;
    @Autowired
    @Qualifier("b")
    private String bBean;
    @Autowired
    @Qualifier("c")
    private String cBean;

    /**
     * spring容器启动完成之后，就会紧接着执行这个接口实现类的run方法。
     * @return
     */
    @Bean
    public ApplicationRunner runner(){
        return args -> {
            System.out.println(allStringBeans);
            System.out.println(aBean);
            System.out.println(bBean);
            System.out.println(cBean);
            System.out.println(groupStringBeans);
        };
    }

    @Bean
    public String a() {
        return "string-a";
    }

    @Bean
//    @Qualifier
    @Group
    public String b() {
        return "string-b";
    }

    @Bean
//    @Qualifier
    @Group
    public String c() {
        return "string-c";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoBootStrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier
@interface Group{}