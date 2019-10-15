package com.gupaovip.service.client;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * ClassName:PersonSink
 * Package:com.gupaovip.service.client
 * description
 * Created by zhangbin on 2019/10/15.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/10/15 16:46
 */
public interface PersonSink {

    @Input("person-sink")
    SubscribableChannel channel();
}
