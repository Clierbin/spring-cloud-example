package com.gupaovip.service.client;

import com.gupaovip.service.client.domain.Person;
import com.gupaovip.service.client.kafka.ObjectSerializer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.*;
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
@EnableBinding({Source.class,PersonSource.class,PersonSink.class})
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

    private final PersonSink personSink;

    public EchoServiceBootStrap(EchoServiceClient echoServiceClient,
//                                RestTemplate restTemplate,
                                KafkaTemplate<String, Object> kafkaTemplate,
                                Source source,
                                PersonSource personSource,
                                PersonSink personSink) {
        this.echoServiceClient = echoServiceClient;
//        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.source = source;
        this.personSource = personSource;
        this.personSink = personSink;
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
        MessageBuilder messageBuilder=MessageBuilder.withPayload(person).setHeader("Content-Type","java/pojo");
        messageChannel.send(messageBuilder.build());
        return person;
    }

    private Person createPerson(String name){
        Person person=new Person();
        person.setId(System.currentTimeMillis());
        person.setName(name);
        return person;
    }

/*    @KafkaListener(topics = "gupao")
    public void listen(Person person){
        System.out.println(person);
    }*/




    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EchoServiceBootStrap.class, args);
    }

    /**
     * 通过 Spring Message API 监听数据
     * @return
     */
    @Bean
    public ApplicationRunner runner(){
        return args -> {
            personSink.channel().subscribe(new MessageHandler() { // 通过 Spring Message API 实现
                @Override
                public void handleMessage(Message<?> message) throws MessagingException {
                    MessageHeaders headers = message.getHeaders();
                    String contentType = headers.get("Content-Type", String.class);
                    Object messagePayload = message.getPayload();
                    System.out.printf("收到消息[主体:%s ,消息头: %s \n" ,messagePayload,contentType);
                }
            });
        };
    }
    // 重要注意事项:
    //1,尽管Spring Cloud Stream Binder 中存在 Kafka 的整合, 然而 Spring Kafka 和 Spring Cloud Stream
    // kafka 在处理数据生产和消费是存在差异的,因此不要混用
    //2,当Spring Cloud Stream 发送消息包含头信息时, kafka Deserializer 实现方法回调时不会予以处理
    //3,通常业务逻辑可以使用@StreamListener 来监听数据 (主体,载体) , 如果是需要更多头信息,需要SubscribableChannel 来实现
    //4,如果同一个应用同时使用 @StreamListener 和 SubscribableChannel 时,两者会轮流处理



    /**
     *  通过注解方式监听
     * @param person
     */
    @StreamListener("person-source") // 指定 Channel 名称
    public void listenFromStream(Person person){
        System.out.println(person);
    }
}

