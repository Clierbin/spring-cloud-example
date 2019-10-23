package com.gupaovip.service.client.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.List;

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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private List<IWebSecurityConfigurer> configurers;

    @PostConstruct
    public void init(){
        AnnotationAwareOrderComparator.sort(configurers);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        configurers.forEach(item->{
            try {
                item.configure(web);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
       configurers.forEach(item ->{
           try {
               item.configure(http);
           } catch (Exception e) {
               e.printStackTrace();
           }
       });
    }
}
