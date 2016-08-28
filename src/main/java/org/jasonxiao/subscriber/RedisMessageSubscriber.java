package org.jasonxiao.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Jason Xiao
 */
@Component
public class RedisMessageSubscriber implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RedisMessageSubscriber.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RedisMessageSubscriber(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        logger.info("Channel: {}", new String(pattern));
        logger.info("Received: {}", message.toString());
        simpMessagingTemplate.convertAndSend("/topic/greetings", message.toString());
    }
}
