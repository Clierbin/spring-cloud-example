package com.gupaovip.com.springconfigclient.configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * ClassName:PropertiesDemo
 * Package:com.gupaovip.com.springconfigclient.configuration
 * description
 * Created by zhangbin on 2019/9/19.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/19 20:36
 */
public class PropertiesDemo {
    public static void main(String[] args) throws IOException {
        Properties properties=new Properties();
        properties.setProperty("name","zhangbin");
        properties.setProperty("age","25");
        properties.storeToXML(System.out,"This a comment","UTF-8");
    }
}
