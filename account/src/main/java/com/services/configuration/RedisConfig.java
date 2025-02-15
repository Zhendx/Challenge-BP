package com.pichincha.business.banking.services.configuration;

import com.pichincha.business.banking.services.repository.impl.AccountRepository;
import com.pichincha.business.banking.services.service.impl.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {
    private final AccountRepository accountRepository;

    @Bean
    public JedisConnectionFactory connectionFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("redis-13802.c322.us-east-1-2.ec2.redns.redis-cloud.com");
        configuration.setPort(13802);
        configuration.setUsername("default");
        configuration.setPassword("1234");
        return new JedisConnectionFactory(configuration);
    }
    @Bean
    public ChannelTopic topic(){
        return new ChannelTopic("client");
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(){
        return new MessageListenerAdapter(new RedisSubscriber(accountRepository), "onMessage");
    }
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addMessageListener(messageListenerAdapter(), topic());
        return container;
    }
}
