package org.jasonxiao.config;

import org.jasonxiao.subscriber.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jason Xiao
 */
@Configuration
public class RedisConfig {

    private final String[] topic;
    private final RedisMessageSubscriber redisMessageSubscriber;

    @Autowired
    public RedisConfig(@Value("${redis.channel.topic}") String[] topic,
                       RedisMessageSubscriber redisMessageSubscriber) {
        this.redisMessageSubscriber = redisMessageSubscriber;
        this.topic = topic;
    }


    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxTotal(25);
        poolConfig.setMaxIdle(25);
        poolConfig.setMinIdle(1);
        poolConfig.setMaxWaitMillis(1000);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnCreate(true);
        poolConfig.setTestOnReturn(true);

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setUsePool(true);
        connectionFactory.setPoolConfig(poolConfig);
        connectionFactory.setTimeout(15);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(redisMessageSubscriber);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListener(), topics());
        return container;
    }

    @Bean
    public List<Topic> topics() {
        List<Topic> topics = new ArrayList<>();
        Arrays.asList(topic).forEach(t -> topics.add(new ChannelTopic(t.trim())));
        return topics;
    }

}
