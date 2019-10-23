package com.gupaovip.service.client.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * ClassName:WebSecurityConfig
 * Package:com.gupaovip.service.client.web.security
 * description
 * Created by zhangbin on 2019/10/17.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/10/17 15:46
 */
//@EnableWebSecurity  // Spring Boot 场景不需要重新标注
@Configuration
@Order(HIGHEST_PRECEDENCE + 1)
public class FormLoginCsrfWebSecurityConfig implements WebMvcConfigurer,IWebSecurityConfigurer {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/*")
                .authenticated()
                .and()
                .formLogin() //Form
        // 登录 激活登录页面/登录操作
        ;
    }
}
