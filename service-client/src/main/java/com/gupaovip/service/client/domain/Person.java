package com.gupaovip.service.client.domain;

import java.io.Serializable;

/**
 * ClassName:Person
 * Package:com.gupaovip.service.client.domain
 * description
 * Created by zhangbin on 2019/9/30.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/30 10:30
 */
public class Person implements Serializable {
    private static final long serialVersionUID = -7291589173754007218L;
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
