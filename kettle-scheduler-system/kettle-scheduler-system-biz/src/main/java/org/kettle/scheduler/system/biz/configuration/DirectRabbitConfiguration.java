package org.kettle.scheduler.system.biz.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-31 07:31
 */
@Configuration
public class DirectRabbitConfiguration {

    //队列 起名：kettleDirectQueue
    @Bean
    public Queue kettleDirectQueue() {
        return new Queue("kettleDirectQueue", true);  //true 是否持久
    }

    //Direct交换机 起名：kettleDirectExchange
    @Bean
    DirectExchange kettleDirectExchange() {
        return new DirectExchange("kettleDirectExchange");
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(kettleDirectQueue()).to(kettleDirectExchange()).with("kettleDirectRouting");
    }
}
