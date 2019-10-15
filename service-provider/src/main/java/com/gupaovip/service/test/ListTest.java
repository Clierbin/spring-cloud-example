package com.gupaovip.service.test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName:ListTest
 * Package:com.gupaovip.service.test
 * description
 * Created by zhangbin on 2019/9/27.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/27 16:43
 */
public class ListTest {
    public static void main(String[] args) {
        List<User> list = Arrays.asList(new User("5月",2,"病区1","科室1"),
                new User("6月",2,"病区1","科室1"),
                new User("5月",2,"病区1","科室2"),
                new User("5月",2,"病区2","科室2"),
                new User("5月",2,"病区2","科室3"),
                new User("5月",2,"病区3","科室1"));

        Map<String, Map<String, List<User>>> collect = list.stream()
                .collect(Collectors.groupingBy(User::getDept, Collectors.groupingBy(User::getCurt)));
        for (String s : collect.keySet()) {
            System.out.println(collect.get(s));
        }



    }
}
