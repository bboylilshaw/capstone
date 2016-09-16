package org.jasonxiao.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Jason Xiao
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> rabbitTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(rabbitTemplate);
        cacheManager.setTransactionAware(true);
        cacheManager.setDefaultExpiration(60); // expire in 1 min
//        cacheManager.setUsePrefix(true);
//        cacheManager.setCachePrefix(new DefaultRedisCachePrefix("~"));
        return cacheManager;
    }
}
