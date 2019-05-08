package com.stackroute.giphermanager.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfiguration {

    @Value("${exchangeName}")
    private String exchangeName;
    @Value("${registerQueue}")
    private String registerQueue;
    @Value("${addRecommendedGifQueue}")
    private String addRecommendedGifQueue;
    @Value("${delRecommendedGifQueue}")
    private String delRecommendedGifQueue;

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Queue queueRegister() {
        return new Queue(registerQueue,false);
    }

    @Bean
    Queue queueAddRecommendedGif() {
        return new Queue(addRecommendedGifQueue,false);
    }

    @Bean
    Queue queueDelRecommendedGif() {
        return new Queue(delRecommendedGifQueue,false);
    }

    @Bean
    Binding bindingUser(Queue queueRegister, DirectExchange exchange) {
        return BindingBuilder.bind(queueRegister()).to(exchange).with("user_routing");
    }

    @Bean
    Binding bindingAddGipher(Queue queueAddRecommendedGif, DirectExchange exchange) {
        return BindingBuilder.bind(queueAddRecommendedGif()).to(exchange).with("gif_add_routing");
    }

    @Bean
    Binding bindingDelGipher(Queue queueDelRecommendedGif, DirectExchange exchange) {
        return BindingBuilder.bind(queueDelRecommendedGif()).to(exchange).with("gif_del_routing");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2JsonMessageConverter());
        return rabbitTemplate;

    }
}
