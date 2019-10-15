package com.gupaovip.service.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

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
public @interface Timeout {
    /**
     * 超时时间
     * @return
     */
    long value();

    /**
     * 时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 补偿方法默认可以为空
     * @return
     */
    String fallback() default "";
}
