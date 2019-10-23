package com.gupaovip.service.client.web.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * ClassName:IWebSecurityConfigurer
 * Package:com.gupaovip.service.client.web.security
 * description
 * Created by zhangbin on 2019/10/23.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/10/23 19:52
 */
public interface IWebSecurityConfigurer {

    default void configure(HttpSecurity http) throws Exception {
        //Do Nothing
    }
    default void configure(WebSecurity web) throws Exception {
        //Do Nothing
    }
}
