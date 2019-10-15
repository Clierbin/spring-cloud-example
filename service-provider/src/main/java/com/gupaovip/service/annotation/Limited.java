package com.gupaovip.service.annotation;

import java.lang.annotation.*;

/**
 * ClassName:Timeout
 * Package:com.gupaovip.service.annotation
 * description
 * Created by zhangbin on 2019/9/27.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/27 16:07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Limited {
    /**
     * 最大限制数量
     * @return
     */
    int value();

}
