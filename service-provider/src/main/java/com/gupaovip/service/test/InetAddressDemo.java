package com.gupaovip.service.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.stream.Stream;

/**
 * ClassName:InetAddressDemo
 * Package:com.gupaovip.service.test
 * description
 * Created by zhangbin on 2019/9/23.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/23 16:25
 */
public class InetAddressDemo {
    public static void main(String[] args) throws UnknownHostException {
        Stream.of(InetAddress.getAllByName("www.baidu.com"))
                .forEach(System.out::println);
    }
}
