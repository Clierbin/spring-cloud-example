package com.gupaovip.service.client.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
@Order
public class CsrfWebSecurityConfig implements WebMvcConfigurer,IWebSecurityConfigurer {


    @Bean
    public CsrfTokenRepository csrfTokenRepository(){
        return  new HttpSessionCsrfTokenRepository();
    }

    @Autowired
    private CsrfTokenRepository csrfTokenRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()  // 激活CSRF(Cross-site request forgery）跨站请求伪造 特性
                .requireCsrfProtectionMatcher(request -> "POST".equalsIgnoreCase(request.getMethod())) // 配置所保护资源请求
                .csrfTokenRepository(csrfTokenRepository) // 替换成 HttpSession 的方式保存
        ;
    }
}
