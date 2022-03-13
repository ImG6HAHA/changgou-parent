package com.changgou;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * AUTHOR: Z
 * CREATE TIME:2022-02-05-14:43
 * 微信支付启动类
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class,args);
    }

    @Autowired
    private Environment env;

    //创建队列
    @Bean
    public Queue createQueue(){
        return new Queue(env.getProperty("mq.pay.queue.order"));
    }

    //创建交换机
    @Bean
    public DirectExchange basicExchanage(){
        return new DirectExchange(env.getProperty("mq.pay.exchange.order"), true,false);
    }

    //绑定
    @Bean
    public Binding basicBinding(){
        return  BindingBuilder.bind(createQueue()).to(basicExchanage()).with(env.getProperty("mq.pay.routing.key"));
    }
}
