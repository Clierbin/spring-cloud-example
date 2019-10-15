package com.gupaovip.service.test;

import lombok.Data;

/**
 * ClassName:User
 * Package:com.gupaovip.service.test
 * description
 * Created by zhangbin on 2019/9/27.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/27 16:41
 */
@Data
public class User {
    private String month;
    private int count;
    private String curt;
    private String dept;

    public User() {
    }

    public User(String month, int count, String curt, String dept) {
        this.month = month;
        this.count = count;
        this.curt = curt;
        this.dept = dept;
    }
}
