package com.gupaovip.service.client;

import com.gupaovip.service.client.domain.Person;
import com.gupaovip.service.client.kafka.ObjectSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName:EchoServiceController
 * Package:com.gupaovip.service.client
 * description
 * Created by zhangbin on 2019/9/22.
 *
 * @author: zhangbin q243132465@163.com
 * @Version 1.0.0
 * @CreateTime： 2019/9/22 18:36
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
@EnableFeignClients
@EnableBinding({Source.class,PersonSource.class})
public class EchoServiceBootStrap {

    private final EchoServiceClient echoServiceClient;
/*    @LoadBalanced
    private final RestTemplate restTemplate;*/

    @Bean
    public ObjectSerializer objectSerializer(){
        return new ObjectSerializer();
    }

    private KafkaTemplate kafkaTemplate;

    private final Source source;

    private final PersonSource personSource;

    public EchoServiceBootStrap(EchoServiceClient echoServiceClient,
//                                RestTemplate restTemplate,
                                KafkaTemplate<String, Object> kafkaTemplate, Source source, PersonSource personSource) {
        this.echoServiceClient = echoServiceClient;
//        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.source = source;
        this.personSource = personSource;
    }

    @GetMapping("/call/echo/{message}")
    public String callEcho(@PathVariable String message) {
        return echoServiceClient.echo(message);
    }

    @GetMapping("/person")
    public Person person(String name){
        Person person = createPerson(name);
        kafkaTemplate.send("gupao",person);
        return person;
    }

    @GetMapping("/stream/person")
    public Person streamPerson(String name){
        Person person = createPerson(name);
        MessageChannel messageChannel = source.output();
        messageChannel.send(MessageBuilder.withPayload(person).build());
        return person;
    }

    @GetMapping("/stream/person/source")
    public Person streamPersonSource(String name){
        Person person = createPerson(name);
        MessageChannel messageChannel = personSource.output();
        messageChannel.send(MessageBuilder.withPayload(person).build());
        return person;
    }

    private Person createPerson(String name){
        Person person=new Person();
        person.setId(System.currentTimeMillis());
        person.setName(name);
        return person;
    }

    @KafkaListener(topics = "gupao")
    public void listen(Person person){
        System.out.println(person);
    }



    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EchoServiceBootStrap.class, args);
    }
}
