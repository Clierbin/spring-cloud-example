package com.gupaovip.service.client;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * ClassName:PersonSource
 * Package:com.gupaovip.service.client
 * description
 * Created by zhangbin on 2019/10/12.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/10/12 18:19
 */
public interface PersonSource {


    @Output("person-source")
    MessageChannel output();
}
