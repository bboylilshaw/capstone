package org.jasonxiao.config;

import org.jasonxiao.websocket.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jason Xiao
 */
@Configuration
public class RedisConfig {

    private final String[] topic;

    @Autowired
    public RedisConfig(@Value("${redis.channel.topic}") String[] topic) {
        this.topic = topic;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
//        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Bean
    public MessageListenerAdapter messageListener(SimpMessagingTemplate messagingTemplate) {
        return new MessageListenerAdapter(new RedisMessageSubscriber(messagingTemplate));
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                        MessageListener messageListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListener, topics());
        return container;
    }

    @Bean
    public List<Topic> topics() {
        List<Topic> topics = new ArrayList<>();
        Arrays.asList(topic).forEach(t -> topics.add(new ChannelTopic(t.trim())));
        return topics;
    }

}
