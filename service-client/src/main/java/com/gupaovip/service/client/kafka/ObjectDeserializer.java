package com.gupaovip.service.client.kafka;

import org.apache.kafka.common.serialization.Deserializer;

import java.io.*;
import java.util.Map;

/**
 * ClassName:ObjectDeserializer
 * Package:com.gupaovip.service.client.kafka
 * description
 * Created by zhangbin on 2019/10/12.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/10/12 16:28
 */
public class ObjectDeserializer implements Deserializer<Serializable> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Serializable deserialize(String topic, byte[] data) {
        Serializable object = null;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            object = (Serializable) ois.readObject();
        } catch (IOException | ClassNotFoundException e) { // Java 7 Multiple Exception Catch
            throw new RuntimeException(e);
        }

        System.out.printf("当前 Topic : %s , 反序列化对象: %s \n", topic, String.valueOf(object));
        return object;
    }

    @Override
    public void close() {

    }
}
