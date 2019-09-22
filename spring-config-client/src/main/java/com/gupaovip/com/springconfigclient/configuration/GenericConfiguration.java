package com.gupaovip.com.springconfigclient.configuration;


/**
 * ClassName:GenericConfiguration
 * Package:com.gupaovip.com.springconfigclient.configuration
 * description
 * Created by zhangbin on 2019/9/19.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/19 20:38
 */
public class GenericConfiguration {
    public static void main(String[] args) {
        println(System.getProperty("user.home"));

        println(System.getProperty("user.age","0"));
        // 将 System properties 装换为Integer 类型
        println(Integer.getInteger("user.age",0));
        println(Boolean.getBoolean("user.male"));
    }

    private static void println(Object object) {
        System.out.println(object);
    }
}
