package com.gupaovip.service.client.kafka;

import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;


/**
 * ClassName:ObjectSerializer
 * Package:com.gupaovip.service.client.kafka
 * description
 * Created by zhangbin on 2019/9/30.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/30 10:58
 */
public class  ObjectSerializer implements Serializer<Serializable> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String topic, Serializable data) {
        System.out.printf("当前 Topic: %s , 序列化对象: %s \n", topic, data);
        byte[] dataArray = null;
        // 在内存里边申请一个
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            // 将对象写入 ObjectOutputStream
            oos.writeObject(data);
            // 将写入后的数据, 通过字节数组方式获取
            dataArray=outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dataArray;
    }

    @Override
    public void close() {

    }
}
